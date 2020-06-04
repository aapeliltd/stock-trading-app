package com.decagonhq.stocktradingapp.api.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.decagonhq.stocktradingapp.api.resource.Stock;


@RestController
public class StockController {
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	
	@GetMapping("/stocktradingapp/stock-current-price")
	public ResponseEntity<Object> stockLookUp(@RequestParam String symbol) {
		//check if symbol is not null
		//symbol is company abbrevation name eg. nflx
		Stock stock = new Stock();
		if(symbol != null) {
			
			stock  =  webClientBuilder.build()
		 				.get()
		 				.uri("/"+symbol+"/quote?token=sk_2c6653931a8847168f59f02fadf624d6") // i added my IEX token here.
		 				.retrieve()
		 				.bodyToMono(Stock.class)
		 				.block();
			
		}
		HashMap<String, String> map = new HashMap<>();
		map.put("click this link to buy stock now", "/stocktradingapp/buy?symbol="+symbol+
		"&price="+stock.getLatestPrice()+"&size="+stock.getIexRealtimeSize());
		stock.setHref(map);
		
		return ResponseEntity.ok(stock);
	}
	
}
