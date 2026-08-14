package com.example.demo.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.demo.entity.StockOrder;
import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.OrderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponse {

	private Long id;
	private String stockId;
	private String stockName;
	private OrderType orderType;
	private OrderStatus orderStatus;
	private BigDecimal price;
	private Long shares;
	private BigDecimal realizedPnl;
	private LocalDateTime createdAt;
	private String failReason;
	
	public static OrderResponse from(StockOrder order) {
		OrderResponse response = new OrderResponse();
		response.setId(order.getId());
		response.setStockId(order.getStock().getStockId());
		response.setStockName(order.getStock().getStockName());
		response.setOrderType(order.getOrderType());
		response.setOrderStatus(order.getOrderStatus());
		response.setPrice(order.getPrice());
		response.setShares(order.getShares());
		response.setRealizedPnl(order.getRealizedPnl());
		response.setCreatedAt(order.getCreatedAt());
		response.setFailReason(order.getFailReason());
	
		return response;
		
	}
}
