package com.example.demo.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AppUser;
import com.example.demo.enums.InitialCapital;
import com.example.demo.enums.Role;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.AppUserRepository;


@Service
public class AppUserService {
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
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
	
	private BigDecimal resolveInitialAssets(InitialCapital initialCapital ) {
		return switch(initialCapital) {
		case TEN_W 			-> new BigDecimal("100000");
		case ONE_HUNDRED_W 	-> new BigDecimal("1000000");
		case ONE_THOUSAND_W -> new BigDecimal("10000000");
		};
		
	}
	
}
