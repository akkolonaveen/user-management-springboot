package com.naveen.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.naveen.dao.CityRepository;
import com.naveen.dao.CountryRepository;
import com.naveen.dao.StateRepository;
import com.naveen.dao.UserRepository;
import com.naveen.model.City;
import com.naveen.model.Country;
import com.naveen.model.State;
import com.naveen.model.User;

import net.bytebuddy.utility.RandomString;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	JavaMailSender mailSender;

	@Override
	public Map<Integer, String> getAllCountries() {

		List<Country> countryList = countryRepository.findAll();

		Map<Integer, String> map = new HashMap<>();
		for (Country c : countryList) {
			map.put(c.getCountryId(), c.getCountryName());
		}
		System.out.println("list " + countryList);
		System.out.println("map " + map);
		return map;
	}

	@Override
	public Map<Integer, String> getStatesByCountryId(Integer countryId) {
		List<State> statesList = stateRepository.findStatesByCountryId(countryId);
		Map<Integer, String> map = new HashMap<>();
		for (State c : statesList) {
			map.put(c.getStateId(), c.getStateName());
		}
		System.out.println("list " + statesList);
		System.out.println("map " + map);
		return map;
	}

	@Override
	public Map<Integer, String> getCitiesByStateId(Integer stateId) {
		List<City> citiesList = cityRepository.findCitiesByStateId(stateId);
		Map<Integer, String> map = new HashMap<>();
		for (City c : citiesList) {
			map.put(c.getCityId(), c.getCityName());
		}
		System.out.println("list " + citiesList);
		System.out.println("map " + map);
		return map;

	}

	@Override
	public boolean emailUnique(String email) {
		User isUniqueEmail = userRepository.findByEmail(email);
		if (isUniqueEmail == null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean registerUser(User user) throws UnsupportedEncodingException, MessagingException {
		String randomCode = RandomString.make(8);
		user.setPassword(randomCode);
		user.setUserAccountStatus("Locked");
		user.setEnabled(false);
		User savedUser = userRepository.save(user);

		String emailBody = getUnlockAccountEmailBody(user);
		String subject = "UNLOCK Your Account ";
		boolean isSent = sendAccountUnlockEmail(subject, emailBody, user.getEmail());
		return savedUser.getUserId() != null & isSent;

	}

	public boolean sendAccountUnlockEmail(String subject, String emailBody, String email) {
		try {

			MimeMessage mail = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mail);
			helper.setTo(email);
			helper.setSubject(subject);
			helper.setText(emailBody, true);
			mailSender.send(mail);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	private String getUnlockAccountEmailBody(User user) {
		StringBuffer sb = new StringBuffer("");
		String body = null;
		try {
			File f = new File("unlock-acc-email-body.txt");

			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();

			}
			br.close();
			body = sb.toString();
			body = body.replace("{FNAME}", user.getFirstName());
			body = body.replace("{LNAME}", user.getLastName());
			body = body.replace("{TEMP-PWD}", user.getPassword());
			body = body.replace("{EMAIL}", user.getEmail());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return body;
	}

	@Override
	public String getRandomPwd() {
		String randomPassword = new Random().ints(8, 33, 122)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		return randomPassword;
	}

	@Override
	public boolean isTempPwdValid(String email, String tempPassword) {
		User tempPwd = userRepository.findByEmailAndPassword(email, tempPassword);
		if (tempPwd != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String loginCheck(String email, String password) {
		User user = userRepository.findByEmailAndPassword(email, password);
		if (user != null) {
			if (user.getUserAccountStatus().equals("Unlocked")) {
				return "Login Successfull,Welcome to AshokIT";
			} else {
				return "Account Locked";
			}

		} else {
			return "Login Failed";
		}
	}

	@Override
	public String forgotPassword(String email) throws UnsupportedEncodingException, MessagingException {
		User user = userRepository.findByEmail(email);

		if (user != null) {
			// send email to the user with password
			String body = user.getPassword();
			String toEmail = user.getEmail();
			String subject = " Reset Forgot Password ";

			sendSimpleEmail(toEmail, body, subject);
			return " password has been sent to email";

		} else {
			return "Invalid Email";
		}

	}

	public void sendSimpleEmail(String toEmail, String body, String subject) {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("akkukittu15@gmail.com");
		message.setTo(toEmail);
		message.setText("your password" + body);
		message.setSubject(subject);

		mailSender.send(message);
		System.out.println("mail send ...");

	}

	@Override
	public User findUserByEmail(String email) {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			return user;
		}
		return null;
	}

	@Override
	public boolean unlockAccount(String email, String newPwd) {
		User userDetails = userRepository.findByEmail(email);
		userDetails.setPassword(newPwd);
		userDetails.setUserAccountStatus("Unlocked");
		try {
			userRepository.save(userDetails);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
