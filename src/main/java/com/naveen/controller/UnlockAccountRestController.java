package com.naveen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.naveen.model.UnlockAccount;
import com.naveen.service.UserService;

@RestController
public class UnlockAccountRestController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/unlockUserAccount")
	public String UnlockUserAccount(@RequestBody UnlockAccount acc)
	{
		
		if(userService.isTempPwdValid(acc.getEmail(),acc.getTempPwd()))
		{
			userService.unlockAccount(acc.getEmail(),acc.getNewPwd());
			
			return "Account Unlocked ,Please proceed with Login";
			
		}
		return "please Enter a Temporary Password";
	}

}
