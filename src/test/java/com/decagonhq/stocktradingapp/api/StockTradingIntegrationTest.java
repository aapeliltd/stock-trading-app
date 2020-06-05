package com.decagonhq.stocktradingapp.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@SpringBootTest( 
		
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = StockTradingAppApplication.class
		
		)
@AutoConfigureMockMvc

public class StockTradingIntegrationTest {

	private final String AUTHORIZATION = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMDEiLCJleHAiOjE1OTEzMjI2OTcsImlhdCI6MTU5MTI1MDY5N30.hopFdM8TZUZrW7SWDlZTFi4XdaxEaLv8lpyu2xch0Zg";
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	public void fundMyAccountTest() throws Exception {
		
		MvcResult  mvcResult = mockMvc.perform( 
				MockMvcRequestBuilders.get("/api/v1/stocktradingapp/fund")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", AUTHORIZATION)
				).andReturn();
		
		System.out.print(mvcResult.getResponse());
	}
	@Test
	public void stockLookUpTest() throws Exception {
		MvcResult  mvcResult = mockMvc.perform( 
				MockMvcRequestBuilders.get("/api/v1/stocktradingapp/stock-current-price")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", AUTHORIZATION)
				.queryParam("symbol", "aapl")
				).andReturn();
		
		System.out.print(mvcResult.getResponse());
	}
	
	@Test
	public void sellStockTest() throws Exception {
		MvcResult  mvcResult = mockMvc.perform( 
				MockMvcRequestBuilders.get("/api/v1/stocktradingapp/stocks/sell")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", AUTHORIZATION)
				.queryParam("id", "2")
				).andReturn();
		
		System.out.print(mvcResult.getResponse());
	}
	@Test
	public void getStockTransactionHhistory() throws Exception {
		MvcResult  mvcResult = mockMvc.perform( 
				MockMvcRequestBuilders.get("/api/v1/stocktradingapp/stocks/transactions")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", AUTHORIZATION)
				).andReturn();
		
		System.out.print(mvcResult.getResponse());
	}
	
	@Test
	public void buyStockTest() throws Exception {
		
		MvcResult  mvcResult = mockMvc.perform( 
				MockMvcRequestBuilders.get("/api/v1/stocktradingapp//stocks/buy")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", AUTHORIZATION)
				.queryParam("symbol", "nflx")
				.queryParam("company", "Nflx Inc")
				.queryParam("price", "100")
				.queryParam("size", "120")
				).andReturn();
		
		System.out.print(mvcResult.getResponse());
	}
	
	@Test
	public void getListOfPurchaseStocksTest() throws Exception {
		MvcResult  mvcResult = mockMvc.perform( 
				MockMvcRequestBuilders.get("/api/v1/stocktradingapp/stocks")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", AUTHORIZATION)
				).andReturn();
		
		System.out.print(mvcResult.getResponse());
	}
	
	@Test
	public void getStockTransactionHhistoryTest() throws Exception {
		MvcResult  mvcResult = mockMvc.perform( 
				MockMvcRequestBuilders.get("/api/v1/stocktradingapp/stocks/transactions")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", AUTHORIZATION)
				).andReturn();
		
		System.out.print(mvcResult.getResponse());
	}
	
	
	
	
	
	
}
