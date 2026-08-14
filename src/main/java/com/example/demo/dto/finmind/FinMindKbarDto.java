package com.example.demo.dto.finmind;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FinMindKbarDto {

	private String date;
	
	private BigDecimal open;
	
	@JsonProperty("max")
	private BigDecimal high;
	
	@JsonProperty("min")
	private BigDecimal low;
	
	private BigDecimal close;
	
	@JsonProperty("Trading_Volume")
	private Long volume;
}
