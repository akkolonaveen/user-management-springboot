package com.naveen.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import org.springframework.web.bind.annotation.RestController;



import com.naveen.service.UserService;

@RestController
public class ForgotPasswordRestController {
	
	private UserService userService;
	
	@GetMapping("/forgotpassword/{email}")
	public String forgotPassword(@PathVariable String email) throws UnsupportedEncodingException, MessagingException
	{
	return 	userService.forgotPassword(email);
	
		
		 
	}
	
	

	
	
	
	

}
