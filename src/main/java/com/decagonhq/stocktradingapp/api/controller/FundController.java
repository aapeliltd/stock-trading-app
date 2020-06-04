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
import com.decagonhq.stocktradingapp.api.model.Transaction;
import com.decagonhq.stocktradingapp.api.model.User;
import com.decagonhq.stocktradingapp.api.repository.FundRepository;
import com.decagonhq.stocktradingapp.api.repository.TransactionRepository;
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
	
	@Autowired
	private TransactionRepository transactionRepository;
	

	
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
			fund = fundRepository.save(fund);
			
			Date date1 = new Date();
			Timestamp created =new Timestamp(date1.getTime());
			
			//update transactions
			Transaction trans = new Transaction(user.getId(), fund.getId(), 1, created, fund.getDescription());
			transactionRepository.save(trans);
			
			
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
		
		double balance = userHeader.getUserBalance(request);
		FundList fundList = new FundList();
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		fundList.setBalance(formatter.format(balance));
		
		return ResponseEntity.ok(fundList);
	}

}
