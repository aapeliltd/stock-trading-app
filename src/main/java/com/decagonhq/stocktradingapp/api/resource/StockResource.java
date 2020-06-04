package com.decagonhq.stocktradingapp.api.resource;

import java.util.HashMap;

public class StockResource {
	
	public String symbol;
	public String company;
	public int size;
	public double bid;
	public double ask;
	public int purchaseId;
	private HashMap<String, String> href;
	private boolean isSold;
	
	public StockResource() {}
	public StockResource(String symbol, String company, int size, double bid, double ask, int purchaseId,
			HashMap<String, String> href) {
		super();
		this.symbol = symbol;
		this.company = company;
		this.size = size;
		this.bid = bid;
		this.ask = ask;
		this.purchaseId = purchaseId;
		this.href = href;
	}
	
	
	public boolean isSold() {
		return isSold;
	}
	public void setSold(boolean isSold) {
		this.isSold = isSold;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public double getBid() {
		return bid;
	}
	public void setBid(double bid) {
		this.bid = bid;
	}
	public double getAsk() {
		return ask;
	}
	public void setAsk(double ask) {
		this.ask = ask;
	}
	public int getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}
	public HashMap<String, String> getHref() {
		return href;
	}
	public void setHref(HashMap<String, String> href) {
		this.href = href;
	}
	
	
	
	
	
	

}
