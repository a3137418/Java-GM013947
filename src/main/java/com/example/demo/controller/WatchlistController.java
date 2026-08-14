package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.stock.StockResponse;
import com.example.demo.dto.watchlist.WatchlistRequest;
import com.example.demo.service.AppUserService;
import com.example.demo.service.WatchlistService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {

    @Autowired
    private WatchlistService watchlistService;
    @Autowired
    private AppUserService appUserService;

    @GetMapping
    public ApiResponse<List<StockResponse>> getWatchlist(Authentication authentication) {
        Long userId = appUserService.getUserByUsername(authentication.getName()).getId();
        return ApiResponse.success("查詢成功", watchlistService.getWatchlist(userId));
    }

    @PostMapping
    public ApiResponse<?> addToWatchlist(@Valid @RequestBody WatchlistRequest request, Authentication authentication) {
        Long userId = appUserService.getUserByUsername(authentication.getName()).getId();
        watchlistService.addToWatchlist(userId, request.getStockId());
        return ApiResponse.success("加入成功", null);
    }

    @DeleteMapping("/{stockId}")
    public ApiResponse<?> removeFromWatchlist(@PathVariable String stockId, Authentication authentication) {
        Long userId = appUserService.getUserByUsername(authentication.getName()).getId();
        watchlistService.removeFromWatchlist(userId, stockId);
        return ApiResponse.success("移除成功", null);
    }
}