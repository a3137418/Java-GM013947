package com.example.demo.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.StockOrder;

public interface StockOrderRepository extends JpaRepository<StockOrder, Long>{
	List<StockOrder> findByUserIdOrderByCreatedAtDesc(Long userId);
	List<StockOrder> findAllByOrderByCreatedAtDesc();
	boolean existsByUserId(Long userId);
}
