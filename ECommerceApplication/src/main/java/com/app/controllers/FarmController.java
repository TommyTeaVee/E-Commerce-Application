package com.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.config.AppConstants;
import com.app.entites.Farm;
import com.app.payloads.FarmDTO;
import com.app.payloads.FarmResponse;
import com.app.services.FarmService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/farmer")
@SecurityRequirement(name = "Farmspehere Backend Application")
public class FarmController {

	@Autowired
	private FarmService farmService;

	@PostMapping("/farm")
	public ResponseEntity<FarmDTO> createFarm(@Valid @RequestBody Farm farm) {
		FarmDTO savedFarmDTO = farmService.createFarm(farm);

		return new ResponseEntity<FarmDTO>(savedFarmDTO, HttpStatus.CREATED);
	}

	@GetMapping("/public/farms")
	public ResponseEntity<FarmResponse> getFarms(
			@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY, required = false) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
		
		FarmResponse farmResponse = farmService.getFarms(pageNumber, pageSize, sortBy, sortOrder);

		return new ResponseEntity<FarmResponse>(farmResponse, HttpStatus.FOUND);
	}

	@PutMapping("/farms/{farmId}")
	public ResponseEntity<FarmDTO> updateFarm(@RequestBody Farm farm,
			@PathVariable Long farmId) {
		FarmDTO farmDTO = farmService.updateFarm(farm, farmId);

		return new ResponseEntity<FarmDTO>(farmDTO, HttpStatus.OK);
	}

	@DeleteMapping("/farms/{farmId}")
	public ResponseEntity<String> deleteFarm(@PathVariable Long farmId) {
		String status = farmService.deleteFarm(farmId);

		return new ResponseEntity<String>(status, HttpStatus.OK);
	}

}
