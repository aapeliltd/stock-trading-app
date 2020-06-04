package com.decagonhq.stocktradingapp.api.resource;


import java.util.HashMap;

public class Stock {
	
	private String symbol;
	private String companyName;
	private double latestPrice;
	private int iexRealtimeSize;
	private boolean isUSMarketOpen;
	private HashMap<String, String> href;
	
	public Stock() {
	
	}
	
	public Stock(String symbol, String companyName, double latestPrice, int iexRealtimeSize, boolean isUSMarketOpen,
			HashMap<String, String> href) {
		super();
		this.symbol = symbol;
		this.companyName = companyName;
		this.latestPrice = latestPrice;
		this.iexRealtimeSize = iexRealtimeSize;
		this.isUSMarketOpen = isUSMarketOpen;
		this.href = href;
	}

	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public double getLatestPrice() {
		return latestPrice;
	}
	public void setLatestPrice(double latestPrice) {
		this.latestPrice = latestPrice;
	}
	public int getIexRealtimeSize() {
		return iexRealtimeSize;
	}
	public void setIexRealtimeSize(int iexRealtimeSize) {
		this.iexRealtimeSize = iexRealtimeSize;
	}
	public boolean isUSMarketOpen() {
		return isUSMarketOpen;
	}
	public void setUSMarketOpen(boolean isUSMarketOpen) {
		this.isUSMarketOpen = isUSMarketOpen;
	}
	public HashMap<String, String> getHref() {
		return href;
	}
	public void setHref(HashMap<String, String> href) {
		this.href = href;
	}
	
	

}
