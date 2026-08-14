package com.example.demo.dto.position;

import java.math.BigDecimal;

import com.example.demo.entity.Position;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PositionResponse {

	private String stockId;
    private String stockName;
    private Long shares;
    private BigDecimal costPrice;
    private BigDecimal currentPrice;
    private BigDecimal unrealizedPnl;
    private BigDecimal previousClose;
    
    
    public static PositionResponse from(Position position) {
    	PositionResponse response = new PositionResponse();
    	response.setStockId(position.getStock().getStockId());
    	response.setStockName(position.getStock().getStockName());
    	response.setShares(position.getShares());
    	response.setCostPrice(position.getCostPrice());
    	response.setCurrentPrice(position.getStock().getPrice());
    	response.setPreviousClose(position.getStock().getPreviousClose());
    	
    	BigDecimal shares = BigDecimal.valueOf(position.getShares());
    	BigDecimal currentPrice = position.getStock().getPrice();
    	BigDecimal costPrice = position.getCostPrice();
    	BigDecimal unrealizedPnl = currentPrice.subtract(costPrice).multiply(shares);
    	response.setUnrealizedPnl(unrealizedPnl);
    	
    	return response;
    }
    
}
