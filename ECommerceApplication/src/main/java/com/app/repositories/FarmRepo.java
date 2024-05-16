package com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entites.Farm;

@Repository
public interface FarmRepo extends JpaRepository<Farm, Long> {

	Farm findByFarmName(String farmName);

}
