package com.naveen.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.naveen.model.City;
@Repository
public interface CityRepository  extends JpaRepository<City,Integer>{
	
	
	List<City> findCitiesByStateId(Integer stateId);

}
