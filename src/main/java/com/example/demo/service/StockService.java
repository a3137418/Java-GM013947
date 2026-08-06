package com.example.demo.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Stock;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.StockRepository;

@Service
public class StockService {

   @Autowired
   private StockRepository stockRepository;

	
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
	
	public Stock findByStockId(String stockId) {
	    return stockRepository.findByStockId(stockId)
	            .orElseThrow(() -> new ResourceNotFoundException("查無此股票"));
	}
}
