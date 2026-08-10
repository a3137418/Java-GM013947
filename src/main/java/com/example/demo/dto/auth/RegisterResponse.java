package com.example.demo.dto.auth;

import java.math.BigDecimal;

import com.example.demo.entity.AppUser;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterResponse {
	private Long id;
	private String username;
	private String email;
	private BigDecimal assets;
	
	public static RegisterResponse from(AppUser user) {
		RegisterResponse response = new RegisterResponse();
		response.setId(user.getId());
		response.setUsername(user.getUsername());
		response.setEmail(user.getEmail());
		response.setAssets(user.getAssets());
		return response;
	}
}
