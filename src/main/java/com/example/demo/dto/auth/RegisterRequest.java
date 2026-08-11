package com.example.demo.dto.auth;

import com.example.demo.enums.InitialCapital;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequest {
	
	@NotBlank(message = "帳號不可空白")
	@Size(min = 3 , max = 50 , message = "帳號長度必須介於 3 到 50")
	private String username;
	
	@NotBlank(message = "密碼不可空白")
	@Size(min = 6 , max = 50 , message = "密碼長度至少 6 個字")
	private String password;
	
	@NotBlank(message = "Email 不可空白")
	@Email(message = "email 格式不正確")
	private String email;
	
	@NotNull(message = "請選擇初始資金")
	private InitialCapital initialCapital;
}
