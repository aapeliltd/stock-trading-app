package com.decagonhq.stocktradingapp.api.resource;

import java.util.List;

import com.decagonhq.stocktradingapp.api.model.Fund;

public class FundList {
	
	private String balance;
	private List<Fund> fund;
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public List<Fund> getFund() {
		return fund;
	}
	public void setFund(List<Fund> fund) {
		this.fund = fund;
	}
	
	

}
