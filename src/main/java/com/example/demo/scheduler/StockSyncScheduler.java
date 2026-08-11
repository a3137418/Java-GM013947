package com.example.demo.scheduler;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.twse.TwseStockDto;
import com.example.demo.service.StockService;

@Component
public class StockSyncScheduler {
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private StockService stockService;
	
	private static final String TWSE_URL = "https://openapi.twse.com.tw/v1/exchangeReport/STOCK_DAY_ALL";
	
	@Scheduled(cron = "0 0 15 * * ?")
//	@Scheduled(cron = "0 */1 * * * ?")  // 每分鐘執行一次，測試用
	public void syncAllStock() {
	 	TwseStockDto[] stocks = restTemplate.getForObject(TWSE_URL, TwseStockDto[].class);
	 	 
	 	for(TwseStockDto dto : stocks) {
	 		String closePrice = dto.getClosingPrice();
	 	// 1. 檢查 closingPrice 是不是 "--" 或空字串，是的話 continue 跳過
	 		if (closePrice == null || closePrice.isBlank() || closePrice.equals("--")) {
				continue;
			}
        // 2. 轉換成 BigDecimal
	 		BigDecimal price = new BigDecimal(closePrice);
        // 3. 呼叫 stockService.syncStock(dto.getCode(), dto.getName(), price)
	 		stockService.syncStock(dto.getCode(), dto.getName(), price);
	 	}
	}
}
