package com.decagonhq.stocktradingapp.api.controller;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.Optional;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.decagonhq.stocktradingapp.api.model.Fund;
import com.decagonhq.stocktradingapp.api.model.Purchase;
import com.decagonhq.stocktradingapp.api.model.Sell;
import com.decagonhq.stocktradingapp.api.model.User;
import com.decagonhq.stocktradingapp.api.model.Withdrawal;
import com.decagonhq.stocktradingapp.api.model.Transaction;

import com.decagonhq.stocktradingapp.api.resource.Message;
import com.decagonhq.stocktradingapp.api.resource.Stock;
import com.decagonhq.stocktradingapp.api.resource.StockListResource;
import com.decagonhq.stocktradingapp.api.resource.StockResource;
import com.decagonhq.stocktradingapp.api.resource.TransactionListResource;
import com.decagonhq.stocktradingapp.api.services.FundService;
import com.decagonhq.stocktradingapp.api.services.PurchaseService;
import com.decagonhq.stocktradingapp.api.services.SellService;
import com.decagonhq.stocktradingapp.api.services.TransactionService;
import com.decagonhq.stocktradingapp.api.services.UserService;
import com.decagonhq.stocktradingapp.api.services.WithdrawalService;

import io.swagger.annotations.ApiOperation;





@RestController
@RequestMapping("/api/v1/stocktradingapp")
public class StockController {
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PurchaseService purchaseService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private WithdrawalService withdrawalService;
	
	@Autowired
	private SellService sellService;
	
	@Autowired
	private FundService fundService;
	
	@GetMapping("/stock-current-price")
	@ApiOperation( value = " Look up current stock price real time by specify symabol name eg aapl for apple",
	notes ="This Api is to get the current price of stock accordingly. You will need to specify symbole name eg. "
			+ "/stock-current-price?symbol=nflx",
	response = Stock.class )
	public ResponseEntity<Object> stockLookUp(@RequestParam String symbol) {
		//check if symbol is not null
		//symbol is company abbrevation name eg. nflx
		Stock stock = new Stock();
		if(symbol != null) {
			
			stock  =  webClientBuilder.build()
		 				.get()
		 				.uri("/"+symbol+"/quote?token=sk_2c6653931a8847168f59f02fadf624d6") // i added my IEX token here.
		 				.retrieve()
		 				.bodyToMono(Stock.class)
		 				.block();
			
		}
		HashMap<String, String> map = new HashMap<>();
		String queryString = String.format("/api/v1/stocktradingapp/stocks/buy?symbol=%s&company=%s&price=%s&size=%s", symbol, stock.getCompanyName().replaceAll("\\s+",""), stock.getLatestPrice(), stock.getIexRealtimeSize());
		map.put("buy", queryString);
		stock.setHref(map);
		return ResponseEntity.ok(stock);
	}
	
	@GetMapping("/stocks/buy")
	@ApiOperation( value = "buy stock here with real time stock price",
	notes ="This Api is used to buy stock as per the real time price.",
	response = Message.class )
	public ResponseEntity<Object> buyStock(@RequestParam Map<String,String> reqParam, HttpServletRequest request) {
		
		String symbol = reqParam.get("symbol");
		String company = reqParam.get("company");
		double price = Double.parseDouble(reqParam.get("price"));
		int size = Integer.parseInt(reqParam.get("size"));
		
		Message msg = new Message();
		
		if (symbol != null && company != null && price >= 0 && size >= 0) {
			
			//check if user have enough fund to buy this stock
			double balance = fundService.getUserBalance(request);
			
			if(balance == 0) {
				
				msg.message = "Insufficient balance, please fund your account and try again";
				return ResponseEntity.ok(msg);
				
			}else {
				
				User user = userService.getUserFromRequestHeader(request);
				
				Date date = new Date();
				Timestamp created =new Timestamp(date.getTime());
				
				Purchase purchase = new Purchase(size, price, created, user.getId(), company, symbol);
				
				//save to purchase table
				purchase =  purchaseService.addNewPurchase(purchase);
				
				String description = String.format("%s withdrew the sum of %s to purchase stock of the same amount", user.getUserName(), price);
				
				//update withdrawal repo
				Withdrawal withdrawal = new Withdrawal(price, description, created, user.getId(), purchase.getId());
				withdrawalService.addNewWithdrawal(withdrawal);
				
				//update transactions history;
				Transaction trans = new Transaction(user.getId(), purchase.getId(), 2, created, purchase.getCompanyName());
				transactionService.addNewTransaction(trans);
				
				msg.message = "Operation was successful.";
				return ResponseEntity.ok(msg);
				
			}
		}

		msg.message = "Something went wrong";
		return ResponseEntity.badRequest().body(msg);
	}
	
	//list all the purchase stocks
	@GetMapping("/stocks")
	@ApiOperation( value = " Look up for the list of purchase stocks",
	notes ="This Api is used to get all the list of purchase stocks",
	response = StockListResource.class )
	public ResponseEntity<Object> getListOfPurchaseStocks(HttpServletRequest request) {
		
		User user = userService.getUserFromRequestHeader(request);
		
		Message  msg = new Message();
		
		if(user != null) {
			
			List<Purchase> purchases = purchaseService.getAllPurchasesByUserId(user.getId());
			if(purchases.size() > 0) {
				
				List<StockResource> resources = new ArrayList<StockResource>();
				purchases.forEach(purchase->{
					HashMap<String, String> map = new HashMap<>();
					String sellUrl = String.format("/api/v1/stocktradingapp/stocks/sell/%s", purchase.getId());
					map.put("Sell", sellUrl);
					
					StockResource stock = new StockResource();
					stock.setCompany(purchase.getCompanyName());
					stock.setSymbol(purchase.getCompanySymbol());
					stock.setSize(purchase.getSize());
					stock.setAsk(purchase.getPrice());
					stock.setBid(purchase.getPrice());
					stock.setPurchaseId(purchase.getId());
					stock.setHref(map);
					stock.setSold(purchase.isSold());
					//StockResource stock = new StockResource(purchase.getCompanySymbol(), purchase.getCompanyName(), 
							//purchase.getSize(), purchase.getPrice(), purchase.getPrice(), purchase.getId(), map);
					resources.add(stock);
				});
				
				StockListResource stockList = new StockListResource();
				stockList.setStocks(resources);
				return ResponseEntity.ok(stockList);
				
			}else {
				msg.message = "You have not purchased any stock yet!";
				return ResponseEntity.ok(msg);
				
			}
		}
		
		msg.message = "Something went wrong.";
		return ResponseEntity.badRequest().body(msg);
				
	}
	
	//lets try and sell stock
	@GetMapping("/stocks/sell/{id}")
	@ApiOperation( value = " Sell your own stock by purchase Id",
	notes ="This Api is used to sell stock and will increase your balance accordingly",
	response = Message.class )
	public ResponseEntity<Object> sellStock(@PathVariable Optional<Integer> id) {
		Message msg = new Message();
		if(id == null) {
			msg.message = "Something went wrong.";
			return ResponseEntity.badRequest().body(msg);
		}
		
		Purchase purchase = purchaseService.getPurchaseById(id);
		
		//check if the stock is not sold yet!
		if(purchase.isSold()) {
			NumberFormat formatter = NumberFormat.getCurrencyInstance();
			String price = formatter.format(purchase.getPrice());
			msg.message = String.format("This stock has been sold on this date: %s at the rate of %s", purchase.getCreated(), price);
			return ResponseEntity.ok(msg);
		}
		
		Date date = new Date();
		Timestamp created =new Timestamp(date.getTime());
		Sell sell = new Sell(purchase.getPrice(), id.get(), created);
		
		//sell stock now
		sell = sellService.addNewSale(sell);
		
		//update purchase to set is_sold to true.
		purchase.setSold(true);
		purchaseService.addNewPurchase(purchase);
		
		//update fund/deposit account
		//because this is income for you
		String description = String.format("Stock sold at the rate of %s", purchase.getPrice());
		Fund fund = new Fund(purchase.getPrice(), description, created, purchase.getUserId());
		fundService.addNewFund(fund);
		
		//update your transaction history
		Transaction trans = new Transaction(purchase.getUserId(), sell.getId(), 3, created, "Sold");
		transactionService.addNewTransaction(trans);
		
		msg.message = "Operation was successful";
		return ResponseEntity.ok(msg);
	}
	
	
	//get stock transaction history
	//takes date range, from and to
	//examples: stocktradingapp/stocks/transactions?from=2019-01-01&to=2020-01-01
	@GetMapping("/stocks/transactions")
	@ApiOperation( value = " Look up for the transactions history",
	notes ="This Api is used to query the transactions history, it also show user account balance",
	response = TransactionListResource.class )
	public ResponseEntity<Object> getStockTransactionHhistory(HttpServletRequest request) {
		User user = userService.getUserFromRequestHeader(request);
		
		Message msg = new Message();
		
		List<Transaction> transactions = transactionService.getAllTransactionsByUserId(user.getId());
		
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		//String price = formatter.format(purchase.getPrice());
		if(transactions.size() > 0) {
			
			List<com.decagonhq.stocktradingapp.api.resource.Transaction> resources = new ArrayList<com.decagonhq.stocktradingapp.api.resource.Transaction>();
			
			transactions.forEach(trans-> {
				
				com.decagonhq.stocktradingapp.api.resource.Transaction res = new com.decagonhq.stocktradingapp.api.resource.Transaction();
				String price = null;
				switch (trans.getOptions()) {
				case 1: //fund
					Optional<Fund> fund = fundService.getFundById(trans.getTransactionId());
					price = formatter.format(fund.get().getAmount());
					res.setFund(price);
					res.setDescription(fund.get().getDescription());
					res.setCreated(fund.get().getCreated());
					break;
				case 2: //purchase
					Optional<Purchase> purchase = purchaseService.getPurchaseById(trans.getTransactionId());
					price = formatter.format(purchase.get().getPrice());
					res.setPurchase("("+price+")");
					res.setDescription(purchase.get().getCompanyName());
					res.setCreated(purchase.get().getCreated());
					break;
				default: //sell : 3
					Optional<Sell> sell = sellService.getSellById(trans.getTransactionId()); 
					price = formatter.format(sell.get().getPrice());
					res.setSell(price);
					res.setDescription("Sold stock");
					res.setCreated(sell.get().getCreated());
					break;
				}
				
				
				resources.add(res);
				
			});
			
			TransactionListResource transList = new TransactionListResource();
			
			double balance = fundService.getUserBalance(request);
			String account_balance  = formatter.format(balance);
			transList.setBalance(account_balance);
			transList.setTransactions(resources);
			return ResponseEntity.ok(transList);
		}
		
	
		
		msg.message = "No transactions yet. Please make use of the Api and come back :)" ;
		return ResponseEntity.badRequest().body(msg);
		
		
	}
}
