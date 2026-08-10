package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.auth.LoginResponse;
import com.example.demo.dto.auth.RegisterRequest;
import com.example.demo.dto.auth.RegisterResponse;
import com.example.demo.entity.AppUser;
import com.example.demo.service.AppUserService;
import com.example.demo.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private AppUserService appUserService;
	
	@Autowired
	private AuthService authService;
	
	// 註冊
	@PostMapping("/register")
	public RegisterResponse register(@RequestBody RegisterRequest request) {
		AppUser user = appUserService.register(
				request.getUsername(), 
				request.getPassword(), 
				request.getEmail(), 
				request.getInitialCapital());
		return RegisterResponse.from(user);
	}
	
	// 登入
	public LoginResponse login() {
		
	}
}
