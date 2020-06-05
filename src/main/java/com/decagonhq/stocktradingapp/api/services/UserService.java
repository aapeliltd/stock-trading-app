package com.decagonhq.stocktradingapp.api.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decagonhq.stocktradingapp.api.model.User;
import com.decagonhq.stocktradingapp.api.repository.UserRepository;
import com.decagonhq.stocktradingapp.api.utility.JsonWebUtility;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private JsonWebUtility jsonWebUtility;
	
	public List<User> getAllUsers() {
		
		return userRepository.findAll();
	}
	
	public User addNewUser(User user) {
		return userRepository.save(user);
	}
	
	public Optional<User> getUserById(int id) {
		return userRepository.findById(id);
	}
	
	public User getUser(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		String token = authorizationHeader.substring(7); //count from Bearer to the space after.
		String userName = jsonWebUtility.extractUsername(token);
		
		User user = userRepository.findByUserName(userName);
		return user;
	}
	
	public User getUserFromRequestHeader(HttpServletRequest request) {
		User user = getUser(request);
		return user;
	}

}
