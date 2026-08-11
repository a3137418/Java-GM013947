package com.example.demo.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {
	@NotBlank(message = "帳號不可空白")
	@Size(min = 3 , max = 50 , message = "帳號長度必須介於 3 到 50")
	private String username;
	
	@NotBlank(message = "密碼不可空白")
	@Size(min = 6 , max = 50 , message = "密碼至少 6 個字")
	private String password;
}
