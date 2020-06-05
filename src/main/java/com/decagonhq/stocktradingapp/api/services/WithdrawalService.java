package com.decagonhq.stocktradingapp.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decagonhq.stocktradingapp.api.model.Withdrawal;
import com.decagonhq.stocktradingapp.api.repository.WithdrawlResipotary;

@Service
public class WithdrawalService {
	
	@Autowired
	private WithdrawlResipotary withdrawlResipotary;
	
	public List<Withdrawal> getAllWithdrawalsByUserId(int userId) {
		return withdrawlResipotary.findAllByUserId(userId);
	}
	
	public Withdrawal addNewWithdrawal(Withdrawal withdrawal) {
		return withdrawlResipotary.save(withdrawal);
	}

}
