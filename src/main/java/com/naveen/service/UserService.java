package com.naveen.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.mail.MessagingException;

import com.naveen.model.User;

public interface UserService {

	public Map<Integer, String> getAllCountries();

	public Map<Integer, String> getStatesByCountryId(Integer countryId);

	public Map<Integer, String> getCitiesByStateId(Integer stateId);

	public boolean emailUnique(String email);

	public boolean registerUser(User user) throws UnsupportedEncodingException, MessagingException;

	public String getRandomPwd();

	public boolean isTempPwdValid(String email, String tempPassword);

	public boolean unlockAccount(String string, String string2);

	public User findUserByEmail(String email);

	public String loginCheck(String email, String Password);

	public String forgotPassword(String email)throws UnsupportedEncodingException, MessagingException;



}
