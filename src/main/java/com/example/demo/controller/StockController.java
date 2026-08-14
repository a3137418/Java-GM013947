package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ApiResponse<List<StockResponse>> getAllStocks(){
		List<Stock> stocks = stockService.getAllStocks();
		List<StockResponse> responses = new ArrayList<>();
		for(Stock stock : stocks) {
			responses.add(StockResponse.from(stock));
		}
		
		return ApiResponse.success("查詢成功", responses);
	}
}
