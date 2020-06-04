package com.decagonhq.stocktradingapp.api.utility;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decagonhq.stocktradingapp.api.model.User;
import com.decagonhq.stocktradingapp.api.repository.UserRepository;

@Service
public class UserHeader {
	
	@Autowired
    private JsonWebUtility jsonWebUtility;
	
	@Autowired
	private UserRepository userRepository;
	
	public User getUserFromRequestHeader(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		String token = authorizationHeader.substring(7); //count from Bearer to the space after.
		String userName = jsonWebUtility.extractUsername(token);
		
		User user = userRepository.findByUserName(userName);
		
		return user;
	}

}
