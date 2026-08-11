package com.example.demo.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.Stock;
import com.example.demo.entity.StockOrder;
import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.OrderType;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.StockOrderRepository;

@Service
public class StockOrderService {

	@Autowired
	private StockOrderRepository stockOrderRepository;
	
	@Autowired
	private AppUserService appUserService;
	
	@Autowired
	private StockService stockService;
	
	@Autowired
	private PositionService positionService;
	
	//買單
	public StockOrder buyOrder(Long userId , String stockId , Long shares) {
		AppUser user = appUserService.getUserById(userId);
		Stock stock = stockService.findByStockId(stockId);
		BigDecimal totalCost = stock.getPrice().multiply(BigDecimal.valueOf(shares));
		
		StockOrder stockOrder = new StockOrder();
		stockOrder.setUser(user);
		stockOrder.setStock(stock);
		stockOrder.setOrderType(OrderType.BUY);
		stockOrder.setPrice(stock.getPrice());
		stockOrder.setShares(shares);
		
		try {
			//帳戶扣款
			appUserService.deductAssets(user, totalCost);
			//更新持倉
			positionService.increasePosition(user, stock, stock.getPrice(), shares);
			//更新訂單狀態
			stockOrder.setOrderStatus(OrderStatus.FILLED);
		} catch (BusinessException e) {
			stockOrder.setOrderStatus(OrderStatus.CANCELLED);
		}
		
		return stockOrderRepository.save(stockOrder);
	}
	
	//賣單
	public StockOrder sellOrder(Long userId , String stockId , Long shares) {
		AppUser user = appUserService.getUserById(userId);
		Stock stock = stockService.findByStockId(stockId);
		BigDecimal inCome = stock.getPrice().multiply(BigDecimal.valueOf(shares));
		
		StockOrder stockOrder = new StockOrder();
		stockOrder.setUser(user);
		stockOrder.setStock(stock);
		stockOrder.setOrderType(OrderType.SELL);
		stockOrder.setPrice(stock.getPrice());
		stockOrder.setShares(shares);
		
		try {
			BigDecimal realized = positionService.decreasePosition(user, stock, stock.getPrice(), shares);
			appUserService.addAssets(user, inCome);
			//更新損益
			stockOrder.setRealizedPnl(realized);
			//更新訂單狀態
			stockOrder.setOrderStatus(OrderStatus.FILLED);
		} catch (BusinessException e) {
			stockOrder.setOrderStatus(OrderStatus.CANCELLED);
		}
	
		return stockOrderRepository.save(stockOrder);
	}
}
