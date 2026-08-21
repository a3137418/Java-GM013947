package com.example.demo.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.aspect.TradeLogAspect;
import com.example.demo.entity.Stock;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.StockRepository;

@Service
public class StockService {

   @Autowired
   private StockRepository stockRepository;

	// 新增股票
   public Stock addStock(String stockId , String stockName , BigDecimal price) {
		if(stockRepository.existsByStockId(stockId)) {
			throw new BusinessException("股票已存在");
		}
		
		Stock stock = new Stock();
		stock.setStockId(stockId);
		stock.setStockName(stockName);
		stock.setPrice(price);
		
		stock = stockRepository.save(stock);
		
		return stock;
		
	}
	
   //查詢股票
	public Stock findByStockId(String stockId) {
	    return stockRepository.findByStockId(stockId)
	            .orElseThrow(() -> new ResourceNotFoundException("查無此股票"));
	}
	
	// 查詢所有股票
	public Page<Stock> getAllStocks(Pageable page , String keyword) {
		if(keyword == null || keyword.isEmpty()) {
			return stockRepository.findAll(page);
		}else {
			return stockRepository.findByStockIdContainingIgnoreCaseOrStockNameContainingIgnoreCase(keyword, keyword, page);
		}
	}
	
	
	// 股票同步
	public void syncStock(String stockId, String stockName, BigDecimal price) {
		Optional<Stock> existing = stockRepository.findByStockId(stockId);
		if(existing.isPresent()) {
			// 已存在，只更新價格
			Stock stock = existing.get();
			if(stock.getUpdatedAt().toLocalDate().isEqual(LocalDate.now())) {
				return;
			}
			stock.setPreviousClose(stock.getPrice());
			stock.setPrice(price);
			stockRepository.save(stock);
		}else {
			// 不存在，新增
			addStock(stockId, stockName, price);
		}
	}
	
	
	// 更新股票現價及昨收價
	public void ensureStockPrice(Stock stock , BigDecimal price , BigDecimal previousClose) {
		stock.setPrice(price);
		stock.setPreviousClose(previousClose);
		stockRepository.save(stock);
	}
	
}
