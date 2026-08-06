package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Postition;

public interface PostitionRepository extends JpaRepository<Postition, Long>{
	List<Postition> findByUserId(Long userId);
	Optional<Postition> findByUserIdAndStockId(Long userId , Long stockId);
	boolean existsByUserId(Long userId);
}
