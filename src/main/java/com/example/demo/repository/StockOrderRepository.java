package com.example.demo.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.StockOrder;

public interface StockOrderRepository extends JpaRepository<StockOrder, Long>{
	List<StockOrder> findByUserIdOrderByCreatedAtDesc(Long userId);
	
	@Query("SELECT so FROM StockOrder so JOIN FETCH so.stock where so.user.id = :userId order by so.createdAt DESC")
	List<StockOrder> findByUserIdWithStockOrderByCreatedAtDesc(@Param("userId") Long userId);
	List<StockOrder> findAllByOrderByCreatedAtDesc();
	boolean existsByUserId(Long userId);
}
