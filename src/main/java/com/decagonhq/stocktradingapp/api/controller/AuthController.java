package com.decagonhq.stocktradingapp.api.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.decagonhq.stocktradingapp.api.model.AuthRequest;
import com.decagonhq.stocktradingapp.api.model.User;
import com.decagonhq.stocktradingapp.api.repository.UserRepository;
import com.decagonhq.stocktradingapp.api.resource.LoginResource;
import com.decagonhq.stocktradingapp.api.resource.Message;
import com.decagonhq.stocktradingapp.api.resource.RegisterResource;
import com.decagonhq.stocktradingapp.api.resource.UserList;
import com.decagonhq.stocktradingapp.api.resource.ValidationResource;
import com.decagonhq.stocktradingapp.api.utility.JsonWebUtility;

import antlr.collections.List;

@RestController
public class AuthController {
	
	
	@Autowired
	private JsonWebUtility jsonWebUtility;
	
	@Autowired
	private AuthenticationManager authenticateManager;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@PostMapping("/stocktradingapp/login")
	public ResponseEntity<LoginResource> login(@RequestBody AuthRequest user) {
		
		// validate username and password and if succeeded, please generate a token for this user.
		try {
			authenticateManager.authenticate(		
					new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
					);
		} catch (Exception e) {
			
			new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		LoginResource login = validateUser(user.getUserName());
		return ResponseEntity.ok(login);
		
	}
	@PostMapping("/stocktradingapp/register")
	public ResponseEntity<Object> register(@RequestBody User user) {
		
		//do some validations
		if(user.getEmail() == null || user.getUserName() == null || user.getPassword() == null) {
			
			ValidationResource validationResource = new ValidationResource();
			validationResource.message = "One of the field is not filled, please check and try again";
			validationResource.date = new Date();
			return ResponseEntity.badRequest().body(validationResource);
		}
		userRepository.save(user);
		
		// then validate user, generate token and return login resource.
		LoginResource login = validateUser(user.getUserName());
		return ResponseEntity.ok(login);
		
		
	}
	
	@GetMapping("/stocktradingapp/users")
	public ResponseEntity<Object> getAllRegisterUsers() {
		
		UserList userList = new UserList();
		java.util.List<User>  theUsers  =  userRepository.findAll();
		userList.users = theUsers;
		if(userList.users.size() > 0 ) {
			return ResponseEntity.ok(userList);
		}else {
			 Message msg = new Message();
			 msg.message = "User list is empty.";
			 return ResponseEntity.ok(msg);
		}
		
		
	}
	
	private LoginResource validateUser(String userName) {
		String token = jsonWebUtility.generateToken(userName);
		LoginResource login = new LoginResource();
		login.username = userName;
		login.token = token;
		return login;
	}
	

}
