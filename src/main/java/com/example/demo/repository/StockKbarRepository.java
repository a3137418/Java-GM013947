package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Stock;
import com.example.demo.entity.StockKbar;
import com.example.demo.enums.KbarType;

public interface StockKbarRepository extends JpaRepository<StockKbar,Long>{
	@Query("select sk from StockKbar sk join fetch sk.stock where sk.stock = :stock and sk.type = :type order by sk.date")
	List<StockKbar> findByStockAndTypeOrderByDateAsc(@Param("stock") Stock stock , @Param("type") KbarType type);
	boolean existsByStockAndType(Stock stock , KbarType type);
	
	Optional<StockKbar> findTopByStockAndTypeOrderByDateDesc(Stock stock , KbarType type);
	
	List<StockKbar> findTop2ByStockAndTypeOrderByDateDesc(Stock stock , KbarType type);
}
