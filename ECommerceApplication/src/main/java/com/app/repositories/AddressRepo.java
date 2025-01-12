package com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entites.Address;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {

	Address findByCountryAndStateAndCityAndZipcodeAndStreetAndBuildingName(String country, String state, String city,
			String zipcode, String street, String buildingName);

}
