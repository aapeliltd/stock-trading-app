package com.decagonhq.stocktradingapp.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.decagonhq.stocktradingapp.api.resource.Welcome;

@RestController
public class HomeController {
	
	@GetMapping("/stocktradingapp")
	public ResponseEntity<Welcome> index() {
		
		String welcome  = "Welcome to our Stock Trading App. Powered by Decagon.";
		
		Welcome wel = new Welcome();
		wel.message = welcome;
		
		return ResponseEntity.ok().body(wel);
	}

}
