package com.naveen.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naveen.model.Country;
@Repository
public interface CountryRepository  extends JpaRepository<Country,Integer>{

	

}
