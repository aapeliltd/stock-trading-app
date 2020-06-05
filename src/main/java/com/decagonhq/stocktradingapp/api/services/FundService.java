package com.decagonhq.stocktradingapp.api.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decagonhq.stocktradingapp.api.model.Fund;
import com.decagonhq.stocktradingapp.api.model.User;
import com.decagonhq.stocktradingapp.api.model.Withdrawal;
import com.decagonhq.stocktradingapp.api.repository.FundRepository;

@Service
public class FundService {
	
	@Autowired
	private FundRepository fundRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WithdrawalService withdrawalService;
	
	public List<Fund> getAllFundsByUserId(int userId) {
		return fundRepository.findAllByUserId(userId);
	}
	
	public Fund addNewFund(Fund fund) {
		return fundRepository.save(fund);
	}
	
	public Optional<Fund> getFundById(int id) {
		return fundRepository.findById(id);
	}
	
	public double getUserBalance(HttpServletRequest request) {
		User user =  userService.getUser(request);
		java.util.List<Fund> debposits = getAllFundsByUserId(user.getId());
		java.util.List<Withdrawal> withdrawals = withdrawalService.getAllWithdrawalsByUserId(user.getId());
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

}
