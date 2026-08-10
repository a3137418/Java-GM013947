package com.example.demo.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter{


	private JwtService jwtService;
	private AppUserDetailsService appUserDetailsService;

	public JwtAuthenticationFilter(JwtService jwtService , AppUserDetailsService appUserDetailsService) {
		this.jwtService = jwtService;
		this.appUserDetailsService = appUserDetailsService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 1.從 request.getHeader("Authorization") 拿到 header
		String authHeader = request.getHeader("Authorization");
		/* 2. 如果是 null 或不是以 "Bearer" 開頭 → 
		 	  直接放行（filterChain.doFilter(request, response); return;），不做任何驗證
		*/
		if(authHeader == null || !authHeader.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		// 3. 用 authHeader.substring(7) 拿掉開頭的 "Bearer "，取得真正的 token 字串
		String token = authHeader.substring(7); // 位置7開始才是 token
		
		// 4. 從token 中取出username
		try {
			String username = jwtService.extractUsername(token);
			
			if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = appUserDetailsService.loadUserByUsername(username);
				
				// 驗證 token
				if(jwtService.isValid(token, userDetails)) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null , userDetails.getAuthorities());
				
					// 設定請求細節
					// WebAuthenticationDetailsSource 會把 Request 資訊放進 Authentication 裡面
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					// 如果 Token 合法有效，就把使用者登入狀態放進 Spring Security Context
					// 將 Authentication 放進 SecurityContext
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		// 5. 最後一定要呼叫 filterChain.doFilter(request, response)，讓請求繼續往下走
		filterChain.doFilter(request, response);
		
	}
}
