package com.example.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entity.StockOrder;
import com.example.demo.entity.TradeLog;
import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.OrderType;
import com.example.demo.repository.TradeLogRepository;

@Aspect
@Component
public class TradeLogAspect {
	
	@Autowired
	private TradeLogRepository tradeLogRepository;
	
	@Around("execution(* com.example.demo.service.StockOrderService.buyOrder(..)) || "
	          + "execution(* com.example.demo.service.StockOrderService.sellOrder(..))")
	public Object logTrade(ProceedingJoinPoint joinPoint) throws Throwable{
		long startTime = System.currentTimeMillis();
		
		Object[] args = joinPoint.getArgs();
		Long userId = (Long) args[0];
		String stockId = (String) args[1];
		Long shares = (Long) args[2];
		OrderType type = joinPoint.getSignature().getName().equals("buyOrder")? OrderType.BUY : OrderType.SELL;
		
		try {
			Object result = joinPoint.proceed();
			long duration = System.currentTimeMillis() - startTime;
			StockOrder order = (StockOrder) result;
			
			TradeLog log = new TradeLog();
			log.setUserId(userId);
			log.setStockId(stockId);
			log.setType(type);
			log.setShares(shares);
			log.setPrice(order.getPrice());
			log.setOrder(order);
			log.setDurationMs(duration);
			log.setSuccess(order.getOrderStatus() == OrderStatus.FILLED);
			log.setFailReason(order.getFailReason());
			tradeLogRepository.save(log);
			
			return result;
		} catch (Throwable e) {
			long duration = System.currentTimeMillis() - startTime;
			
			TradeLog log = new TradeLog();
            log.setUserId(userId);
            log.setStockId(stockId);
            log.setType(type);
            log.setShares(shares);
            log.setDurationMs(duration);
            log.setSuccess(false);
            log.setFailReason(e.getMessage());
            tradeLogRepository.save(log);

            throw e;
			
		}
	}
}
