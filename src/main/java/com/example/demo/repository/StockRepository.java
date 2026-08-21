package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Stock;



public interface StockRepository extends JpaRepository<Stock, Long>{
	Optional<Stock>  findByStockId(String stockId);
	boolean existsByStockId(String stockId);
	
	Page<Stock> findByStockIdContainingIgnoreCaseOrStockNameContainingIgnoreCase(
			String stockId , String stockName , Pageable pageable);
}
