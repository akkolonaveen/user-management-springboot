package com.naveen.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.mail.MessagingException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.naveen.model.User;

import com.naveen.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
   
	
	@GetMapping("/countries")
	public Map<Integer,String> getCountries(){
	return userService.getAllCountries();
	}
	@GetMapping("/states/{countryId}")
	public Map<Integer,String> getStatesByCountryId(@PathVariable
	Integer countryId){
	return userService.getStatesByCountryId(countryId);
	}
	@GetMapping("/cities/{stateId}")
	public Map<Integer,String> getCitiesByStateId(@PathVariable
	Integer stateId){
	return userService.getCitiesByStateId(stateId);
	}
	
	@GetMapping("/emailCheck/{email}")
	public String emailCheck(@PathVariable String email){
	boolean isUnique = userService.emailUnique(email);
	if(isUnique){
	return "UNIQUE";
	}
	return "DUPLICATE";
	}
	
	@PostMapping("/saveUser")
	public ResponseEntity<String> registerUser(@RequestBody
	User user) throws UnsupportedEncodingException, MessagingException{
		if(userService.emailUnique(user.getEmail()))
		{
		userService.registerUser(user);
		return new ResponseEntity<>("Registration success",
				HttpStatus.CREATED);
			
		}
	
	 return new ResponseEntity<>("Registration Failed",
	HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	
	   
	    
	  

}
