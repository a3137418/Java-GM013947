package com.example.demo.config;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.demo.security.AppUserDetailsService;
import com.example.demo.security.JwtAuthenticationFilter;
import com.example.demo.security.JwtService;

@Configuration
public class SecurityConfig {
	

	
	// 設定密碼加密方式
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService , AppUserDetailsService appUserDetailsService) {
		return new JwtAuthenticationFilter(jwtService, appUserDetailsService);
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http , JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable) // 關閉 CSRF
				.cors(Customizer.withDefaults()) // 啟用CORS(設定前端合法位置, 請參考下方 corsConfigurationSource 方法)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 設定 Stateless Session
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/auth/register" , "/api/auth/login").permitAll()
						.requestMatchers("/api/stock/**").permitAll()
						.requestMatchers("/api/kbar/**").permitAll()
						.anyRequest().authenticated())
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	 }
	 
	 // 設定 AuthenticationProvider
	 @Bean
	 public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService , PasswordEncoder passwordEncoder) {
		 DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
		 provider.setPasswordEncoder(passwordEncoder);
		 return provider;
	 }
	 
	// 設定 AuthenticationManager
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource(
            @Value("${app.cors.allowed-origins}") String allowedOrigins) {
		
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
