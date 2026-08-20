package com.example.demo.service;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.finmind.FinMindKbarDto;
import com.example.demo.dto.finmind.FinMindResponse;
import com.example.demo.dto.kbar.StockKbarResponse;
import com.example.demo.entity.Stock;
import com.example.demo.entity.StockKbar;
import com.example.demo.enums.KbarType;
import com.example.demo.repository.StockKbarRepository;



@Service
public class StockKbarService {

	@Autowired
	private StockKbarRepository stockKbarRepository;
	@Autowired
	private StockService stockService;
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${app.finmind.token}")
	private String finmindToken;
	
	private static final String FINMIND_URL = "https://api.finmindtrade.com/api/v4/data";
	
	public List<StockKbarResponse> getKbar(String stockId , KbarType type ){
		Stock stock = stockService.findByStockId(stockId);
		Optional<StockKbar> newDay =stockKbarRepository.findTopByStockAndTypeOrderByDateDesc(stock, type);
		
		if(newDay.isEmpty()) {
			fetchAndSaveFromFinMind(stock, type ,LocalDate.now().minusMonths(3));
		}else if(!newDay.get().getDate().equals(LocalDate.now())){
			fetchAndSaveFromFinMind(stock, type , newDay.get().getDate().plusDays(1));
		}else {
		}
		List<StockKbar> kbars = stockKbarRepository.findByStockAndTypeOrderByDateAsc(stock, type);
		List<StockKbarResponse> responses = new ArrayList<>();
		for(StockKbar kbar : kbars) {
			responses.add(StockKbarResponse.from(kbar));
		}
		return responses;
	}
	
	public void fetchAndSaveFromFinMind(Stock stock , KbarType type , LocalDate startDate) {
		String url = FINMIND_URL +  "?dataset=TaiwanStockPrice&data_id=" + stock.getStockId()
								+ "&start_date=" + startDate + "&token=" + finmindToken;
		
		FinMindResponse response = restTemplate.getForObject(url, FinMindResponse.class);
		
		List<StockKbar> kbars = new ArrayList<>();
		for(FinMindKbarDto dto : response.getData()) {
			StockKbar kbar = new StockKbar();
			kbar.setStock(stock);
			kbar.setType(type);
			kbar.setDate(LocalDate.parse(dto.getDate()));
			kbar.setOpen(dto.getOpen());
			kbar.setHigh(dto.getHigh());
			kbar.setLow(dto.getLow());
			kbar.setClose(dto.getClose());
			kbar.setVolume(dto.getVolume());
			kbars.add(kbar);
		}
		stockKbarRepository.saveAll(kbars);
	}
	
}
