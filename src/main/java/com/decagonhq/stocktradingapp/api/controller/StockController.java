package com.decagonhq.stocktradingapp.api.controller;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.decagonhq.stocktradingapp.api.model.Fund;
import com.decagonhq.stocktradingapp.api.model.Purchase;
import com.decagonhq.stocktradingapp.api.model.Sell;
import com.decagonhq.stocktradingapp.api.model.User;
import com.decagonhq.stocktradingapp.api.model.Withdrawal;
import com.decagonhq.stocktradingapp.api.model.Transaction;
import com.decagonhq.stocktradingapp.api.repository.FundRepository;
import com.decagonhq.stocktradingapp.api.repository.PurchaseRepository;
import com.decagonhq.stocktradingapp.api.repository.SellRepository;
import com.decagonhq.stocktradingapp.api.repository.TransactionRepository;
import com.decagonhq.stocktradingapp.api.repository.WithdrawlResipotary;
import com.decagonhq.stocktradingapp.api.resource.Message;
import com.decagonhq.stocktradingapp.api.resource.Stock;
import com.decagonhq.stocktradingapp.api.resource.StockListResource;
import com.decagonhq.stocktradingapp.api.resource.StockResource;
import com.decagonhq.stocktradingapp.api.resource.TransactionListResource;
import com.decagonhq.stocktradingapp.api.utility.UserHeader;




@RestController
public class StockController {
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Autowired
	private UserHeader userHeader;
	
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private WithdrawlResipotary withdrawalRepository;
	
	@Autowired
	private SellRepository sellRepository;
	
	@Autowired
	private FundRepository FundRepository;
	
	@GetMapping("/stocktradingapp/stock-current-price")
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
		String queryString = String.format("/stocktradingapp/stocks/buy?symbol=%s&company=%s&price=%s&size=%s", symbol, stock.getCompanyName().replaceAll("\\s+",""), stock.getLatestPrice(), stock.getIexRealtimeSize());
		map.put("buy", queryString);
		stock.setHref(map);
		return ResponseEntity.ok(stock);
	}
	
	@GetMapping("stocktradingapp/stocks/buy")
	public ResponseEntity<Object> buyStock(@RequestParam Map<String,String> reqParam, HttpServletRequest request) {
		
		String symbol = reqParam.get("symbol");
		String company = reqParam.get("company");
		double price = Double.parseDouble(reqParam.get("price"));
		int size = Integer.parseInt(reqParam.get("size"));
		
		Message msg = new Message();
		
		if (symbol != null && company != null && price >= 0 && size >= 0) {
			
			//check if user have enough fund to buy this stock
			double balance = userHeader.getUserBalance(request);
			
			if(balance == 0) {
				
				msg.message = "Insufficient balance, please fund your account and try again";
				return ResponseEntity.ok(msg);
				
			}else {
				
				User user = userHeader.getUserFromRequestHeader(request);
				
				Date date = new Date();
				Timestamp created =new Timestamp(date.getTime());
				
				Purchase purchase = new Purchase(size, price, created, user.getId(), company, symbol);
				
				//save to purchase table
				purchase =  purchaseRepository.save(purchase);
				
				String description = String.format("%s withdrew the sum of %s to purchase stock of the same amount", user.getUserName(), price);
				
				//update withdrawal repo
				Withdrawal withdrawal = new Withdrawal(price, description, created, user.getId(), purchase.getId());
				withdrawalRepository.save(withdrawal);
				
				//update transactions history;
				Transaction trans = new Transaction(user.getId(), purchase.getId(), 2, created, purchase.getCompanyName());
				transactionRepository.save(trans);
				
				msg.message = "Operation was successful.";
				return ResponseEntity.ok(msg);
				
			}
		}

		msg.message = "Something went wrong";
		return ResponseEntity.badRequest().body(msg);
	}
	
	//list all the purchase stocks
	@GetMapping("/stocktradingapp/stocks")
	public ResponseEntity<Object> getListOfPurchaseStocks(HttpServletRequest request) {
		
		User user = userHeader.getUserFromRequestHeader(request);
		
		Message  msg = new Message();
		
		if(user != null) {
			
			List<Purchase> purchases = purchaseRepository.findAllByUserId(user.getId());
			if(purchases.size() > 0) {
				
				List<StockResource> resources = new ArrayList<StockResource>();
				purchases.forEach(purchase->{
					HashMap<String, String> map = new HashMap<>();
					String sellUrl = String.format("/stocktradingapp/stocks/sell/%s", purchase.getId());
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
	@GetMapping("/stocktradingapp/stocks/sell/{id}")
	public ResponseEntity<Object> sellStock(@PathVariable Optional<Integer> id) {
		Message msg = new Message();
		if(id == null) {
			msg.message = "Something went wrong.";
			return ResponseEntity.badRequest().body(msg);
		}
		
		Purchase purchase = purchaseRepository.findById(id);
		
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
		sell = sellRepository.save(sell);
		
		//update purchase to set is_sold to true.
		Purchase purchase2 = purchaseRepository.findById(id);
		purchase2.setSold(true);
		purchaseRepository.save(purchase2);
		
		//update fund/deposit account
		//because this is income for you
		String description = String.format("Stock sold at the rate of %s", purchase.getPrice());
		Fund fund = new Fund(purchase.getPrice(), description, created, purchase.getUserId());
		FundRepository.save(fund);
		
		//update your transaction history
		Transaction trans = new Transaction(purchase.getUserId(), sell.getId(), 3, created, "Sold");
		transactionRepository.save(trans);
		
		msg.message = "Operation was successful";
		return ResponseEntity.ok(msg);
	}
	
	
	//get stock transaction history
	//takes date range, from and to
	//examples: stocktradingapp/stocks/transactions?from=2019-01-01&to=2020-01-01
	@GetMapping("/stocktradingapp/stocks/transactions")
	public ResponseEntity<Object> getStockTransactionHhistory(HttpServletRequest request) {
		User user = userHeader.getUserFromRequestHeader(request);
		
		Message msg = new Message();
		
		List<Transaction> transactions = transactionRepository.findAllByUserId(user.getId());
		
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		//String price = formatter.format(purchase.getPrice());
		if(transactions.size() > 0) {
			
			List<com.decagonhq.stocktradingapp.api.resource.Transaction> resources = new ArrayList<com.decagonhq.stocktradingapp.api.resource.Transaction>();
			
			transactions.forEach(trans-> {
				
				com.decagonhq.stocktradingapp.api.resource.Transaction res = new com.decagonhq.stocktradingapp.api.resource.Transaction();
				String price = null;
				switch (trans.getOptions()) {
				case 1: //fund
					Optional<Fund> fund = FundRepository.findById(trans.getTransactionId());
					price = formatter.format(fund.get().getAmount());
					res.setFund(price);
					res.setDescription(fund.get().getDescription());
					res.setCreated(fund.get().getCreated());
					break;
				case 2: //purchase
					Optional<Purchase> purchase = purchaseRepository.findById(trans.getTransactionId());
					price = formatter.format(purchase.get().getPrice());
					res.setPurchase("("+price+")");
					res.setDescription(purchase.get().getCompanyName());
					res.setCreated(purchase.get().getCreated());
					break;
				default: //sell : 3
					Optional<Sell> sell = sellRepository.findById(trans.getTransactionId()); 
					price = formatter.format(sell.get().getPrice());
					res.setSell(price);
					res.setDescription("Sold stock");
					res.setCreated(sell.get().getCreated());
					break;
				}
				
				
				resources.add(res);
				
			});
			
			TransactionListResource transList = new TransactionListResource();
			
			double balance = userHeader.getUserBalance(request);
			String account_balance  = formatter.format(balance);
			transList.setBalance(account_balance);
			transList.setTransactions(resources);
			return ResponseEntity.ok(transList);
		}
		
	
		
		msg.message = "No transactions yet. Please make use of the Api and come back :)" ;
		return ResponseEntity.badRequest().body(msg);
		
		
	}
}
