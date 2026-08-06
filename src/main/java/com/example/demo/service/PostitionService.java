package com.example.demo.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.Postition;
import com.example.demo.entity.Stock;
import com.example.demo.repository.PostitionRepository;

@Service
public class PostitionService {

	@Autowired
	private PostitionRepository postitionRepository;
	
	
	
	//買進
	public Postition increasePostition(AppUser user , Stock stock , BigDecimal price , Long shares) {
		Optional<Postition> existing = postitionRepository.findByUserIdAndStockId(user.getId(), stock.getId());
		
		
		Postition postition;
		if(existing.isEmpty()) {
			//沒有持倉，新增
			postition = new Postition();
			postition.setUser(user);
			postition.setStock(stock);
			postition.setCostPrice(price);
			postition.setShares(shares);
		}else {
			//已有持倉，補上加權平均邏輯
			postition = existing.get();
			// TODO:加權平均計算
			BigDecimal oldShares = BigDecimal.valueOf(postition.getShares());
			BigDecimal newShares =  BigDecimal.valueOf(shares);
			//總股數
			BigDecimal totalShares = oldShares.add(newShares);
			//舊成本
			BigDecimal oldcost = postition.getCostPrice().multiply(oldShares);
			//新成本
			BigDecimal newcost = price.multiply(newShares);
			//總成本
			BigDecimal totalcost = oldcost.add(newcost);
			//新平均價位
			BigDecimal newcostprice = totalcost.divide(totalShares , 2 , RoundingMode.HALF_UP) ;
			postition.setCostPrice(newcostprice);
			postition.setShares(postition.getShares() + shares);	
		}
		postitionRepository.save(postition);	
		
		return postition;
	}
	
	
	//賣出
	public BigDecimal decreasePosition(AppUser user , Stock stock , BigDecimal sellPrice , Long shares) {
		
		
		
		
		return null;
	}
	
}
