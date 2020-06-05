package com.decagonhq.stocktradingapp.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.decagonhq.stocktradingapp.api.model.AuthRequest;
import com.decagonhq.stocktradingapp.api.model.User;
import com.decagonhq.stocktradingapp.api.resource.LoginResource;
import com.decagonhq.stocktradingapp.api.resource.Message;
import com.decagonhq.stocktradingapp.api.resource.UserList;
import com.decagonhq.stocktradingapp.api.resource.UserResource;
import com.decagonhq.stocktradingapp.api.resource.ValidationResource;
import com.decagonhq.stocktradingapp.api.services.UserService;
import com.decagonhq.stocktradingapp.api.utility.JsonWebUtility;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/stocktradingapp")
public class AuthController {
	
	
	@Autowired
	private JsonWebUtility jsonWebUtility;
	
	@Autowired
	private AuthenticationManager authenticateManager;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/login")
	@ApiOperation( value = "Login with username and password",
	notes ="This Api is used to login to our application by providing username and password and token will be generated"
			+ "for subsequent login",
	response = LoginResource.class )
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
	@PostMapping("/register")
	@ApiOperation( value = " register in our application user email, username and password",
	notes ="This Api is used to register in our application and return token for subsequent login",
	response = LoginResource.class )
	public ResponseEntity<Object> register(@RequestBody User user) {
		
		//do some validations
		if(user.getEmail() == null || user.getUserName() == null || user.getPassword() == null) {
			
			ValidationResource validationResource = new ValidationResource();
			validationResource.message = "One of the field is not filled, please check and try again";
			validationResource.date = new Date();
			return ResponseEntity.badRequest().body(validationResource);
		}
		userService.addNewUser(user);
		
		// then validate user, generate token and return login resource.
		LoginResource login = validateUser(user.getUserName());
		return ResponseEntity.ok(login);
		
		
	}
	
	@GetMapping("/users")
	@ApiOperation( value = "Look for the list of users",
	notes ="This Api is used to fetch the list of users in the database",
	response = UserList.class )
	public ResponseEntity<Object> getAllRegisterUsers() {
		
		
		
		
		UserList userList = new UserList();
		java.util.List<UserResource> resources = new ArrayList<UserResource>();
		java.util.List<User> theUsers  =  userService.getAllUsers();
		
		if(theUsers.size() > 0 ) {
			int total = 0;
			for(User user: theUsers) {
				HashMap<String, String> map = new HashMap<>();
				total++;
				UserResource userResource = new UserResource();
				userResource.setEmail(user.getEmail());
				userResource.setUsername(user.getUserName());
				map.put("link", "/stocktradingapp/users/"+user.getId());
				map.put("Account balance", "/stocktradingapp/funds/balance/");
				userResource.setHref(map);
				resources.add(userResource);
				
			}
			userList.setTotal(total);
			userList.setUsers(resources);
			return ResponseEntity.ok(userList);
		}else {
			Message msg = new Message();
			msg.message = "Something went wrong";
			return ResponseEntity.badRequest().body(msg);
		}
		
		
	}
	@GetMapping("/users/{id}")
	@ApiOperation( value = "Search user by Id",
	notes ="This Api is used to get user by user Id",
	response = UserResource.class )
	public ResponseEntity<Object> getUserById(@PathVariable Optional<Integer> id) {
		if(id != null) {
			
		 Optional<User> user = userService.getUserById(id.get());
		 HashMap<String, String> map = new HashMap<>();
		 UserResource userResource = new UserResource();
		 userResource.setEmail(user.get().getEmail());
		 userResource.setUsername(user.get().getUserName());
		 map.put("link", "/stocktradingapp/users/"+user.get().getId());
		 map.put("Account balance", "/stocktradingapp/funds/balance/");
		 userResource.setHref(map);
		 
		 return ResponseEntity.ok(userResource);
		}
		
		Message msg = new Message();
		msg.message = "Something went wrong";
		return ResponseEntity.badRequest().body(msg);
	}
	
	private LoginResource validateUser(String userName) {
		String token = jsonWebUtility.generateToken(userName);
		LoginResource login = new LoginResource();
		login.username = userName;
		login.token = token;
		return login;
	}
	

}
