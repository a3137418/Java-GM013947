package com.example.demo.dto.kbar;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.demo.entity.StockKbar;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StockKbarResponse {

	private String stockId;
	private String stockName;
	private LocalDate date;
	private BigDecimal open;
	private BigDecimal high;
	private BigDecimal low;
	private BigDecimal close;
	private Long volume;
	
	public static StockKbarResponse from(StockKbar kbar) {
		StockKbarResponse response = new StockKbarResponse();
		response.setStockId(kbar.getStock().getStockId());
		response.setStockName(kbar.getStock().getStockName());
		response.setDate(kbar.getDate());
		response.setOpen(kbar.getOpen());
		response.setHigh(kbar.getHigh());
		response.setLow(kbar.getLow());
		response.setClose(kbar.getClose());
		response.setVolume(kbar.getVolume());
		return response;
	}
	
}
