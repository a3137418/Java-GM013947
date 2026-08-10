package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.dto.auth.LoginRequest;
import com.example.demo.dto.auth.LoginResponse;
import com.example.demo.entity.AppUser;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.security.JwtService;

@Service
public class AuthService {
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	/*
	 * 登入驗證
	 * 流程:
	 * 1.將前端輸入的帳號與密碼封裝成 UsernamePasswordAuthenticationToken
	 * 2.交由 AuthenticationManger 進行 Spring Security 標準認證
	 * 3.認證成功後重新查詢使用者資料
	 * 4.依據 username 與 role 產生 JWT Token
	 * 5.回傳 Token 類型，Token 字串與使用者基本資料
	 * 
	 * 注意:
	 * 此方法不直接進行比對密碼，而是交由 Spring Security 來管理
	 * 這樣就可以保持安全驗證流程一致
	 * */
	public LoginResponse login(LoginRequest request) {
		// 1.將前端輸入的帳號與密碼封裝成 UsernamePasswordAuthenticationToken
		Authentication auth = new UsernamePasswordAuthenticationToken(request.getUsername() , request.getPassword());
		
		// 2.交由 AuthenticationManger 進行 Spring Security 標準認證
		authenticationManager.authenticate(auth);
		
		// 3.認證成功後重新查詢使用者資料
		AppUser user = appUserRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new BusinessException("找不到使用者"));
		// 4.依據 username 與 role 產生 JWT Token
		String token = jwtService.generateToken(user.getUsername() , user.getRole().name());
		
		
		LoginResponse response = new LoginResponse();
		response.setToken(token);
		response.setTokenType("Bearer");
		// 5.回傳 Token 類型，Token 字串與使用者基本資料
		return response;
	}
	
}
