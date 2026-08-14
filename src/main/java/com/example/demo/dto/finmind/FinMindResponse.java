package com.example.demo.dto.finmind;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FinMindResponse {

	private String msg;
	private int status;
	private List<FinMindKbarDto> data;
}
