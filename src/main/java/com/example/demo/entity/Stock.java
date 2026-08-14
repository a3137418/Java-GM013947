package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "app_stock")
@Getter
@Setter
@NoArgsConstructor
public class Stock {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//股票代號
	@Column(nullable = false , unique = true , length =  10)
	private String stockId;
	
	//股票名稱
	@Column(nullable = false , unique = true , length = 50)
	private String stockName;
	
	//現價
	@Column(nullable = false , precision = 10 , scale = 2)
	private BigDecimal price;
	
	// 昨收價
	@Column(name = "previous_close" , precision = 10 , scale = 2)
	private BigDecimal previousClose;
	
	//價格更新時間
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
}
