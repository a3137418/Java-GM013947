package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Position;
import org.springframework.data.repository.query.Param;

public interface PositionRepository extends JpaRepository<Position, Long>{
	List<Position> findByUserId(Long userId);
	@Query("SELECT p FROM Position p JOIN FETCH p.stock WHERE p.user.id = :userId")
	List<Position> findByUserIdWithStock(@Param("userId") Long userId);
	Optional<Position> findByUserIdAndStockId(Long userId , Long stockId);
	boolean existsByUserId(Long userId);
}
