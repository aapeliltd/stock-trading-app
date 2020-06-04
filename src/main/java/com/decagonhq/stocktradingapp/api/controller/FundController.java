package com.decagonhq.stocktradingapp.api.controller;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.decagonhq.stocktradingapp.api.model.Fund;
import com.decagonhq.stocktradingapp.api.model.FundRequest;
import com.decagonhq.stocktradingapp.api.model.User;
import com.decagonhq.stocktradingapp.api.repository.FundRepository;
import com.decagonhq.stocktradingapp.api.repository.UserRepository;
import com.decagonhq.stocktradingapp.api.resource.FundList;
import com.decagonhq.stocktradingapp.api.resource.Message;
import com.decagonhq.stocktradingapp.api.utility.JsonWebUtility;
import com.decagonhq.stocktradingapp.api.utility.UserHeader;

import io.netty.handler.codec.http.HttpRequest;

@RestController
public class FundController {
	
	
	
	@Autowired
	private FundRepository fundRepository;
	
	@Autowired
	private UserHeader userHeader;
	

	
	@PostMapping("/stocktradingapp/fund")
	public ResponseEntity<Object> fundMyAccount(@RequestBody FundRequest fundRequest, HttpServletRequest request) {
		
		try {
			User user = userHeader.getUserFromRequestHeader(request);
			
			Fund fund = new Fund();
			fund.setUser_id(user.getId());
			fund.setAmount(fundRequest.getAmount());
			fund.setDescription(fundRequest.getDescription());
			Date date = new Date();
			Timestamp ts=new Timestamp(date.getTime());
			fund.setCreated(ts);
			fundRepository.save(fund);
			
			Message success = new Message();
			success.message = "The sum of "+ fund.getAmount() + " was added to your account successfully.";
			return ResponseEntity.ok(success);
		} catch (Exception e) {
			
			Message msg = new Message();
			msg.message = "Something went wrong, kindly check back.";
			
			return ResponseEntity.badRequest().body(msg);
			
		}
		 
		
		
	}
	
	@GetMapping("/stocktradingapp/funds/balance")
	public ResponseEntity<Object> getFunds(HttpServletRequest request) {
		
		User user = userHeader.getUserFromRequestHeader(request);
		
		List<Fund> funds = fundRepository.findAllByUserId(user.getId());
		double total = 0;
		if(funds.size() > 0) {
			for(final Fund fund : funds) {
				total += fund.getAmount();
			}
		}
		FundList fundList = new FundList();
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		fundList.setBalance(formatter.format(total));
		fundList.setFund(funds);
		
		return ResponseEntity.ok(fundList);
	}

}
