package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.demo.enums.OrderStatus;
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
@Table(name = "stock_order")
@Getter
@Setter
@NoArgsConstructor
public class StockOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY , optional = false)
	@JoinColumn(name = "user_id" , nullable = false)
	private AppUser user;
	
	@ManyToOne(fetch = FetchType.LAZY , optional = false)
	@JoinColumn(name = "stock_id" , nullable = false)
	private Stock stock;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false , length = 10)
	private OrderType orderType;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false , length = 30)
	private OrderStatus orderStatus;
	
	@Column(nullable = false , precision = 10 , scale = 2)
	private BigDecimal price;
	
	@Column(nullable = false)
	private Long shares;
	
	@Column(name = "realized_pnl" , precision = 10 ,scale = 2)
	private BigDecimal realizedPnl;
	
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;
}
