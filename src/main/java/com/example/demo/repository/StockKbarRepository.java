package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Stock;
import com.example.demo.entity.StockKbar;
import com.example.demo.enums.KbarType;

public interface StockKbarRepository extends JpaRepository<StockKbar,Long>{
	
	List<StockKbar> findByStockAndTypeOrderByDateAsc(Stock stock , KbarType type);
	boolean existsByStockAndType(Stock stock , KbarType type);
	
}
