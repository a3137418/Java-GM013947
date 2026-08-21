package com.example.demo.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.example.demo.enums.KbarType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.position.PositionResponse;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.Position;
import com.example.demo.entity.Stock;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.PositionRepository;

@Service
public class PositionService {

	@Autowired
	private PositionRepository positionRepository;
	
	@Autowired
	private StockKbarService stockKbarService;
	
	
	//買進
	public Position increasePosition(AppUser user , Stock stock , BigDecimal price , Long shares) {
		Optional<Position> existing = positionRepository.findByUserIdAndStockId(user.getId(), stock.getId());
		
		
		Position position;
		if(existing.isEmpty()) {
			//沒有持倉，新增
			position = new Position();
			position.setUser(user);
			position.setStock(stock);
			position.setCostPrice(price);
			position.setShares(shares);
		}else {
			//已有持倉，補上加權平均邏輯
			position = existing.get();
			// TODO:加權平均計算
			BigDecimal oldShares = BigDecimal.valueOf(position.getShares());
			BigDecimal newShares =  BigDecimal.valueOf(shares);
			//總股數
			BigDecimal totalShares = oldShares.add(newShares);
			//舊成本
			BigDecimal oldcost = position.getCostPrice().multiply(oldShares);
			//新成本
			BigDecimal newcost = price.multiply(newShares);
			//總成本
			BigDecimal totalcost = oldcost.add(newcost);
			//新平均價位
			BigDecimal newcostprice = totalcost.divide(totalShares , 2 , RoundingMode.HALF_UP) ;
			position.setCostPrice(newcostprice);
			position.setShares(position.getShares() + shares);	
		}
		positionRepository.save(position);	
		
		return position;
	}
	
	
	//賣出
	public BigDecimal decreasePosition(AppUser user , Stock stock , BigDecimal sellPrice , Long shares) {
		Optional<Position> existing = positionRepository.findByUserIdAndStockId(user.getId(), stock.getId());
		
		Position position;
		BigDecimal realized;
		if(existing.isEmpty()) {
			throw new BusinessException("該股票無持股");
		}else {
			position = existing.get();
			BigDecimal oldShares = BigDecimal.valueOf(position.getShares());
			BigDecimal newShares =  BigDecimal.valueOf(shares);
			if (shares > position.getShares()) {
				throw new BusinessException("股數不足");
			}else {
				BigDecimal totalShares = oldShares.subtract(newShares);
				realized = (sellPrice.subtract(position.getCostPrice())).multiply(newShares);
				
				if (totalShares.compareTo(BigDecimal.ZERO) == 0) {
					positionRepository.delete(position);
				}else {
					position.setShares(position.getShares() - shares );
					positionRepository.save(position);
				}
			}
		}
		
		return realized;
	}
	
	// 查詢持股
	public List<PositionResponse> getPositionsByUser(Long userId){
		List<Position> positions = positionRepository.findByUserIdWithStock(userId);
		List<PositionResponse> responses = new ArrayList<>();
		
		for(Position position : positions) {
			stockKbarService.syncPriceFromKbar(position.getStock(), KbarType.DAY);
			responses.add(PositionResponse.from(position));
		}
		return responses;
	}
}
