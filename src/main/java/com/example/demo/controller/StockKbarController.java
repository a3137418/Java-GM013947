package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.kbar.StockKbarResponse;
import com.example.demo.enums.KbarType;
import com.example.demo.service.StockKbarService;

@RestController
@RequestMapping("/api/kbar/{stockId}")
public class StockKbarController {

	@Autowired
	private StockKbarService stockKbarService;
	
	
	@GetMapping
	public ApiResponse<List<StockKbarResponse>> getKbars(@PathVariable String stockId){
		
		KbarType type = KbarType.DAY;
		List<StockKbarResponse> kbars = stockKbarService.getKbar(stockId, type);
		return ApiResponse.success("數據讀取成功", kbars);
	}
}
