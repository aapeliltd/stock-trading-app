package com.decagonhq.stocktradingapp.api.controller;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.decagonhq.stocktradingapp.api.model.Fund;
import com.decagonhq.stocktradingapp.api.model.FundRequest;
import com.decagonhq.stocktradingapp.api.model.Transaction;
import com.decagonhq.stocktradingapp.api.model.User;
import com.decagonhq.stocktradingapp.api.resource.FundList;
import com.decagonhq.stocktradingapp.api.resource.Message;
import com.decagonhq.stocktradingapp.api.services.FundService;
import com.decagonhq.stocktradingapp.api.services.TransactionService;
import com.decagonhq.stocktradingapp.api.services.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/stocktradingapp")
public class FundController {
	
	
	
	@Autowired
	private FundService fundService;
	
	@Autowired
	private UserService userServices;
	
	@Autowired
	private TransactionService transactionService;
	

	
	@PostMapping("/fund")
	@ApiOperation( value = " fund or deposit your account or wallet by the amount specified",
	notes ="This Api is used to add deposit or to fund your wallet",
	response = Message.class )
	public ResponseEntity<Object> fundMyAccount(@RequestBody FundRequest fundRequest, HttpServletRequest request) {
		
		try {
			User user = userServices.getUserFromRequestHeader(request);
			
			Fund fund = new Fund();
			fund.setUser_id(user.getId());
			fund.setAmount(fundRequest.getAmount());
			fund.setDescription(fundRequest.getDescription());
			Date date = new Date();
			Timestamp ts=new Timestamp(date.getTime());
			fund.setCreated(ts);
			fund = fundService.addNewFund(fund);
			
			Date date1 = new Date();
			Timestamp created =new Timestamp(date1.getTime());
			
			//update transactions
			Transaction trans = new Transaction(user.getId(), fund.getId(), 1, created, fund.getDescription());
			transactionService.addNewTransaction(trans);
			
			
			Message success = new Message();
			success.message = "The sum of "+ fund.getAmount() + " was added to your account successfully.";
			return ResponseEntity.ok(success);
		} catch (Exception e) {
			
			Message msg = new Message();
			msg.message = "Something went wrong, kindly check back.";
			
			return ResponseEntity.badRequest().body(msg);
			
		}
		 
		
		
	}
	
	@GetMapping("/funds/balance")
	@ApiOperation( value = " Look for your user balance. get your real time balance",
	notes ="This Api call use to retrieve user account balance",
	response = FundList.class )
	public ResponseEntity<Object> getFunds(HttpServletRequest request) {
		
		double balance = fundService.getUserBalance(request);
		FundList fundList = new FundList();
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		fundList.setBalance(formatter.format(balance));
		
		return ResponseEntity.ok(fundList);
	}

}
