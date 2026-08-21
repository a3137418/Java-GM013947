package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.stock.StockResponse;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.Stock;
import com.example.demo.entity.Watchlist;
import com.example.demo.enums.KbarType;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.WatchListRepository;

@Service
public class WatchlistService {

    @Autowired
    private WatchListRepository watchListRepository;
    @Autowired
    private StockKbarService stockKbarService;
    
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private StockService stockService;

    // 加入自選
    public void addToWatchlist(Long userId, String stockId) {
        AppUser user = appUserService.getUserById(userId);
        Stock stock = stockService.findByStockId(stockId);

        if (watchListRepository.existsByUserAndStock(user, stock)) {
            throw new BusinessException("已經在自選清單中");
        }

        Watchlist watchlist = new Watchlist();
        watchlist.setUser(user);
        watchlist.setStock(stock);
        watchListRepository.save(watchlist);
    }

    // 移除自選
    public void removeFromWatchlist(Long userId, String stockId) {
        AppUser user = appUserService.getUserById(userId);
        Stock stock = stockService.findByStockId(stockId);

        Optional<Watchlist> existing = watchListRepository.findByUserAndStock(user, stock);
        if (existing.isEmpty()) {
            throw new BusinessException("該股票不在自選清單中");
        }
        watchListRepository.delete(existing.get());
    }

    // 查詢自選清單
    public List<StockResponse> getWatchlist(Long userId) {
        List<Watchlist> watchlists = watchListRepository.findByUserWithStock(userId);
        List<StockResponse> responses = new ArrayList<>();
        for (Watchlist watchlist : watchlists) {
        	stockKbarService.syncPriceFromKbar(watchlist.getStock() , KbarType.DAY);
            responses.add(StockResponse.from(watchlist.getStock()));
        }
        return responses;
    }
}