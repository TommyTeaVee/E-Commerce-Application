package com.app.services;


import com.app.entites.Farm;
import com.app.payloads.FarmDTO;
import com.app.payloads.FarmResponse;

public interface FarmService {

	FarmDTO createFarm(Farm farm);

	FarmResponse getFarms(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

	FarmDTO updateFarm(Farm farm, Long farmId);

	String deleteFarm(Long farmId);
}
