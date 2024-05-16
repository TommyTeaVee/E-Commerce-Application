package com.app.services;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.app.payloads.*;
import com.app.repositories.FarmRepo;
import com.app.services.CartService;
import com.app.services.FarmService;
import com.app.services.FileService;
import com.app.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.entites.Cart;
import com.app.entites.Farm;
import com.app.entites.Product;
import com.app.exceptions.APIException;
import com.app.exceptions.ResourceNotFoundException;
import com.app.repositories.CartRepo;

import com.app.repositories.ProductRepo;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class FarmServiceImpl implements FarmService {

	@Autowired
	private FarmRepo farmRepo;

	@Autowired
	private ProductService productService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public FarmDTO createFarm(Farm farm) {
		Farm savedFarm = farmRepo.findByFarmName(farm.getFarmName());

		if (savedFarm != null) {
			throw new APIException("Farm with the name '" + farm.getFarmName() + "' already exists !!!");
		}

		savedFarm = farmRepo.save(farm);

		return modelMapper.map(savedFarm, FarmDTO.class);
	}

	@Override
	public FarmResponse getFarms(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
		Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

		Page<Farm> pageFarms = farmRepo.findAll(pageDetails);

		List<Farm> categories = pageFarms.getContent();

		if (categories.size() == 0) {
			throw new APIException("No farm is created till now");
		}

		List<FarmDTO> farmDTOs = categories.stream()
				.map(farm -> modelMapper.map(farm, FarmDTO.class)).collect(Collectors.toList());

		FarmResponse farmResponse = new FarmResponse();

		farmResponse.setContent(farmDTOs);
		farmResponse.setPageNumber(pageFarms.getNumber());
		farmResponse.setPageSize(pageFarms.getSize());
		farmResponse.setTotalElements(pageFarms.getTotalElements());
		farmResponse.setTotalPages(pageFarms.getTotalPages());
		farmResponse.setLastPage(pageFarms.isLast());

		return farmResponse;
	}

	@Override
	public FarmDTO updateFarm(Farm farm, Long farmId) {
		Farm savedFarm = farmRepo.findById(farmId)
				.orElseThrow(() -> new ResourceNotFoundException("Farm", "farmId", farmId));

		farm.setFarmId(farmId);

		savedFarm = farmRepo.save(farm);

		return modelMapper.map(savedFarm, FarmDTO.class);
	}

	@Override
	public String deleteFarm(Long farmId) {
		Farm farm = farmRepo.findById(farmId)
				.orElseThrow(() -> new ResourceNotFoundException("Farm", "farmId", farmId));

		List<Product> products = farm.getProducts();

		products.forEach(product -> {
			productService.deleteProduct(product.getProductId());
		});

		farmRepo.delete(farm);

		return "Farm with farmId: " + farmId + " deleted successfully !!!";
	}

}