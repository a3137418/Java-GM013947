package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.demo.enums.OrderType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class TradeLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_id" , nullable = false)
	private Long userId;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false , length = 10)
	private OrderType type;
	
	@Column(name = "stock_id" , nullable = false , length = 10)
	private String stockId;
	
	@ManyToOne(fetch = FetchType.LAZY , optional = true)
	@JoinColumn(name = "order_id" , nullable = true)
	private StockOrder order;
	
	@Column(nullable = false)
	private Long shares;
	
	@Column(nullable = true , precision = 10 , scale = 2)
	private BigDecimal price;
	
	@Column(name = "times" , nullable = false)
	private Long durationMs;
	
	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	private boolean success;
	
	@Column(name = "fail_reason")
	private String failReason;
	
	
}
