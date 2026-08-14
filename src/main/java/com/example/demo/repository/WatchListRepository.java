package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.Stock;
import com.example.demo.entity.Watchlist;


public interface WatchListRepository extends JpaRepository<Watchlist, Long>{

	boolean existsByUserAndStock(AppUser user , Stock stock);
	
	Optional<Watchlist> findByUserAndStock(AppUser user , Stock stock);
	@Query("SELECT w FROM Watchlist w JOIN FETCH w.stock WHERE w.user.id = :userId")
	List<Watchlist> findByUserWithStock(@Param("userId") Long userId);
}
