package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.order.OrderRequest;
import com.example.demo.dto.order.OrderResponse;
import com.example.demo.entity.StockOrder;
import com.example.demo.service.AppUserService;
import com.example.demo.service.StockOrderService;

@RestController
@RequestMapping("/api/order")
public class StockOrderController {

	@Autowired
	private StockOrderService stockOrderService;
	
	@Autowired
	private AppUserService appUserService;
	
	// 下單買進
	@PostMapping("/buy")
	public ApiResponse<OrderResponse> buy(@RequestBody OrderRequest request , Authentication authentication){
		String username = authentication.getName();
		Long userId = appUserService.getUserByUsername(username).getId();
		StockOrder order = stockOrderService.buyOrder(userId, request.getStockId(), request.getShares());
		return ApiResponse.success("下單成功", OrderResponse.from(order));
		
	}
	
	//下單賣出
	@PostMapping("/sell")
	public ApiResponse<OrderResponse> sell(@RequestBody OrderRequest request , Authentication authentication){
		String username = authentication.getName();
		Long userId = appUserService.getUserByUsername(username).getId();
		StockOrder order = stockOrderService.sellOrder(userId, request.getStockId(), request.getShares());
		return ApiResponse.success("下單成功", OrderResponse.from(order));
		
	}
}
