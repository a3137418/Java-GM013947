package com.example.demo.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequest {
	@NotNull
	private String stockId;
	@NotNull
	private Long shares;
}
