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
    //市值
    private BigDecimal marketValue;
    //成本
    private BigDecimal costValue;
    
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
    	response.setMarketValue(currentPrice.multiply(shares));
    	response.setCostValue(costPrice.multiply(shares));
    	
    	return response;
    }
    
}
