package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.auth.LoginRequest;
import com.example.demo.dto.auth.LoginResponse;
import com.example.demo.dto.auth.RegisterRequest;
import com.example.demo.dto.auth.UserResponse;
import com.example.demo.entity.AppUser;
import com.example.demo.service.AppUserService;
import com.example.demo.service.AuthService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class AuthController {


	@Autowired
	private AppUserService appUserService;
	
	@Autowired
	private AuthService authService;


	
	@PostMapping("/register")
	public ApiResponse<UserResponse> register(@Valid @RequestBody RegisterRequest request){
		AppUser user = appUserService.register(
				request.getUsername(), 
				request.getPassword(), 
				request.getEmail(), 
				request.getInitialCapital());
		UserResponse response = UserResponse.from(user);
		return ApiResponse.created("註冊成功", response);
	}
	
	// 登入
	@PostMapping("/login")
	public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
		LoginResponse response = authService.login(request);
		return ApiResponse.success("登入成功", response);
	}
	
	
	// 查詢我的資產
	@GetMapping("/me")
	public ApiResponse<UserResponse> getMe(Authentication authentication){
		String username = authentication.getName();
		AppUser user = appUserService.getUserByUsername(username);
		return ApiResponse.success("查詢成功", UserResponse.from(user));
	}
}
