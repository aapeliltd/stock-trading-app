package com.decagonhq.stocktradingapp.api.utility;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decagonhq.stocktradingapp.api.model.Fund;
import com.decagonhq.stocktradingapp.api.model.User;
import com.decagonhq.stocktradingapp.api.model.Withdrawal;
import com.decagonhq.stocktradingapp.api.repository.FundRepository;
import com.decagonhq.stocktradingapp.api.repository.UserRepository;
import com.decagonhq.stocktradingapp.api.repository.WithdrawlResipotary;



@Service
public class UserHeader {
	
	@Autowired
    private JsonWebUtility jsonWebUtility;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FundRepository fundRepository;
	
	@Autowired
	private WithdrawlResipotary withdrawalRepository;
	
	public User getUserFromRequestHeader(HttpServletRequest request) {
		User user = getUser(request);
		return user;
	}
	
	public double getUserBalance(HttpServletRequest request) {
		User user = getUser(request);
		java.util.List<Fund> debposits = fundRepository.findAllByUserId(user.getId());
		java.util.List<Withdrawal> withdrawals = withdrawalRepository.findAllByUserId(user.getId());
		if(debposits.size() > 0 || withdrawals.size() > 0) {
			double totalDeposit = 0;
			double totalWithdrawal = 0;
			for(Fund fund : debposits) {
				totalDeposit += fund.getAmount();
			}
			for(Withdrawal withdrawal : withdrawals) {
				totalWithdrawal += withdrawal.getAmount();
			}
			double total = totalDeposit - totalWithdrawal;
			return total;
		}
		return 0;
	}
	
	private User getUser(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		String token = authorizationHeader.substring(7); //count from Bearer to the space after.
		String userName = jsonWebUtility.extractUsername(token);
		
		User user = userRepository.findByUserName(userName);
		return user;
	}

}
