package com.app.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FarmDTO {

	private Long farmId;
	private String farmName;
private List<ProductDTO> products = new ArrayList<>();
}
