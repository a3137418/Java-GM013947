package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.stock.StockResponse;
import com.example.demo.entity.Stock;
import com.example.demo.service.StockService;

@RestController
@RequestMapping("/api/stock")
public class StockController {
	
	@Autowired
	private StockService stockService;
	
	@GetMapping
	public ApiResponse<Page<StockResponse>> getAllStocks(
			@RequestParam(defaultValue = "0") int page , 
			@RequestParam(defaultValue = "10") int sizes,
			@RequestParam(required = false) String keyword){
		Pageable pageable = PageRequest.of(page, sizes);
		
		Page<Stock> stocks = stockService.getAllStocks(pageable , keyword);
		
		Page<StockResponse> responses = stocks.map(StockResponse::from);
		
		return ApiResponse.success("查詢成功", responses);
	}
}
