package com.example.demo.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AppUser;
import com.example.demo.enums.InitialCapital;
import com.example.demo.enums.Role;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AppUserRepository;


@Service
public class AppUserService {

	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//註冊
	public AppUser register(
			String username, 
			String password, 
			String email,
			InitialCapital initialCapital) {
		if(appUserRepository.existsByUsername(username)) {
			throw new BusinessException("帳號已存在");
		}
		
		AppUser user = new AppUser();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setEmail(email);
		user.setAssets(resolveInitialAssets(initialCapital));
		user.setRole(Role.USER);
		
		user = appUserRepository.save(user);
		
		return user;
	}
	//設定初始金額
	private BigDecimal resolveInitialAssets(InitialCapital initialCapital ) {
		return switch(initialCapital) {
		case TEN_W 			-> new BigDecimal("100000");
		case ONE_HUNDRED_W 	-> new BigDecimal("1000000");
		case ONE_THOUSAND_W -> new BigDecimal("10000000");
		};
		
	}
	
	//扣款
	public AppUser deductAssets(AppUser user , BigDecimal amount) {
		BigDecimal newAssets;
		//1.檢查餘額夠不夠
		if(user.getAssets().compareTo(amount) < 0) {
			//2.不夠->丟例外
			throw new BusinessException("餘額不足");
		}else {
			//3.夠-> user.getAssets() 減去 amount，setAssets，save
			newAssets = user.getAssets().subtract(amount);
			user.setAssets(newAssets);
			appUserRepository.save(user);
		}
		return user;
	}
	
	//入賬
	public AppUser addAssets(AppUser user , BigDecimal amount) {
		BigDecimal newAssets;
		newAssets = user.getAssets().add(amount);
		user.setAssets(newAssets);
		appUserRepository.save(user);
		
		return user;
	}
	
	//查詢使用者ID
	public AppUser getUserById(Long userId) {
		return appUserRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("找不到使用者"));
	}
	
	//查詢使用者姓名
	public AppUser getUserByUsername(String username) {
		return appUserRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("找不到使用者"));
	}
	
	
}
