package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.demo.enums.KbarType;

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
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stock_kbar" , uniqueConstraints = @UniqueConstraint(columnNames = {"stock_id" , "date" , "type"}))
@Getter
@Setter
@NoArgsConstructor
public class StockKbar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY , optional = false)
	@JoinColumn(name = "stock_id" , nullable = false)
	private Stock stock;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false , length = 10)
	private KbarType type;
	
	
	@Column(name = "date")
	private LocalDate date;
	
	@Column(nullable = false , precision = 10 , scale = 2)
	private BigDecimal open;
	
	@Column(nullable = false , precision = 10 , scale = 2)
	private BigDecimal high;
	
	@Column(nullable = false , precision = 10 , scale = 2)
	private BigDecimal low;
	
	@Column(nullable = false , precision = 10 , scale = 2)
	private BigDecimal close;
	
	@Column(nullable = false)
	private Long volume;
	
	@CreationTimestamp
	@Column(name = "created_at" , updatable = false)
	private LocalDateTime createdAt;
}
