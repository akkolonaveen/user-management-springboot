package com.naveen.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.naveen.model.User;
@Repository
public interface UserRepository  extends JpaRepository<User,Integer>{

	
	User findByEmail(String email);
	
   
     
	User findByEmailAndPassword(String email, String password);
	

}
