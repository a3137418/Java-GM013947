package com.example.demo.dto.twse;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TwseStockDto {

	@JsonProperty("Code")
	private String code;
	
	@JsonProperty("Name")
	private String name;
	
	@JsonProperty("ClosingPrice")
	private String closingPrice;
}
