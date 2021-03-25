package com.naveen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.naveen.service.UserService;

@RestController
public class LoginRestController {
	@Autowired
	private UserService userService;
	@GetMapping("/login/{email}/{password}")
	public String login(@PathVariable String email,@PathVariable String password)
	{
		return userService.loginCheck(email, password);
	}

}
