package com.decagonhq.stocktradingapp.api.resource;

import java.util.Date;

public class Transaction {
	
	private String fund;
	private String purchase;
	private String sell;
	private String description;
	private Date created;
	
	public Transaction() {
		
	}

	public Transaction(String fund, String purchase, String sell, String description, Date created) {
		super();
		this.fund = fund;
		this.purchase = purchase;
		this.sell = sell;
		this.description = description;
		this.created = created;
	}

	public String getFund() {
		return fund;
	}

	public void setFund(String fund) {
		this.fund = fund;
	}

	public String getPurchase() {
		return purchase;
	}

	public void setPurchase(String purchase) {
		this.purchase = purchase;
	}

	public String getSell() {
		return sell;
	}

	public void setSell(String sell) {
		this.sell = sell;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	


	
}
