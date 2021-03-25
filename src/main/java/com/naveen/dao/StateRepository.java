package com.naveen.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import com.naveen.model.State;
@Repository
public interface StateRepository  extends JpaRepository<State,Integer>{

  
	
	List<State> findStatesByCountryId(Integer countryId);

	

	

}
