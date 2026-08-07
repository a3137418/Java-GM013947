package com.example.demo.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


/*
 * JWT 服務類別
 * 
 * 負責處理 JWT Token 相關功能
 * 
 * 主要服務:
 * 1.產生 JWT Token 
 * 2.從 JWT Token 解析 username
 * 3.驗證 JWT Token 是否有效
 * 4.驗證 JWT Token 是否過期
 * 5.解析 JWT 裡面的 Claims 資料
 * 
 * 使用者登入之後，系統會產生一組 JWT Token 給前端
 * 之後每次前端調用後端服務的時候會把這組 Token 放在 Authorization Header 裡面迴船後端
 * 後端再透過此 JWTService 判斷 Token 是否正確
 * */
@Service
public class JwtService {

	private SecretKey key;// JWT 簽章用密鑰
	private long expirationMinutes;// JWT Token 的有效時間
	
	/*
	 * app.jwt.secret 與 app.jwt.expiration-minutes
	 * 都來自於 application.properties 的設定內容
	 * */
	public JwtService(@Value("${app.jwt.secret}") String secret ,
			@Value("${app.jwt.expiration-minutes}") long expirationMinutes) {
		this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.expirationMinutes = expirationMinutes;
	}
	
	/*
	 * 產生 JWT Token
	 * */
	public String generateToken(String username , String role) {
		Instant now = Instant.now();
		return Jwts.builder()
				.subject(username)
				.claim("role", role)
				.issuedAt(Date.from(now))
				.expiration(Date.from(now.plus(expirationMinutes , ChronoUnit.MINUTES)))
				.signWith(key , Jwts.SIG.HS256)
				.compact();
	}
}
