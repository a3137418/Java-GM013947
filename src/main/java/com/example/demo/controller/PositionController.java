package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.position.PositionResponse;
import com.example.demo.service.AppUserService;
import com.example.demo.service.PositionService;

@RestController
@RequestMapping("/api/position")
public class PositionController {

	@Autowired
	private PositionService positionService;
	
	@Autowired
	private AppUserService appUserService;
	
	@GetMapping
	public ApiResponse<List<PositionResponse>> getMyPositions(Authentication authentication){
		String username = authentication.getName();
		Long userId = appUserService.getUserByUsername(username).getId();
		List<PositionResponse> positions = positionService.getPositionsByUser(userId);
		return ApiResponse.success("查詢成功", positions);
	}
}
