package com.example.demo.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
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
@Table(name = "watchlist", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "stock_id"}))
@Getter
@Setter
@NoArgsConstructor
public class Watchlist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY , optional = false)
	@JoinColumn(name = "user_id" , nullable = false)
	private AppUser user;
	
	@ManyToOne(fetch = FetchType.LAZY , optional = false)
	@JoinColumn(name = "stock_id" , nullable = false)
	private Stock stock;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
}
