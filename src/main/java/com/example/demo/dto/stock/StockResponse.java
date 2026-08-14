package com.example.demo.dto.stock;

import java.math.BigDecimal;

import com.example.demo.entity.Stock;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StockResponse {

	private String stockId;
	private String stockName;
	private BigDecimal price;
	private BigDecimal previousClose;
	
	public static StockResponse from(Stock stock) {
		
		StockResponse response = new StockResponse();
		response.setStockId(stock.getStockId());
		response.setStockName(stock.getStockName());
		response.setPreviousClose(stock.getPreviousClose());
		response.setPrice(stock.getPrice());
		
		return response;
	}
}
