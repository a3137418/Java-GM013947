package com.example.demo.dto.auth;

import com.example.demo.enums.InitialCapital;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequest {
	private String username;
	private String password;
	private String email;
	private InitialCapital initialCapital;
}
