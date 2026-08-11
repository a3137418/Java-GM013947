package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "position" , uniqueConstraints = @UniqueConstraint(columnNames = {"user_id" , "stock_id"}))
@Getter
@Setter
@NoArgsConstructor
public class Position {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY , optional = false)
	@JoinColumn(name = "user_id" , nullable = false)
	private AppUser user;
	
	@ManyToOne(fetch = FetchType.LAZY , optional = false)
	@JoinColumn(name = "stock_id" , nullable = false)
	private Stock stock;
	
	@Column(name = "cost_price" , nullable = false , precision = 10 , scale = 2)
	private BigDecimal costPrice;
	
	@Column(name = "shares" , nullable = false)
	private Long shares;
	
	@CreationTimestamp
	@Column(name = "created_at" , updatable = false)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
}
