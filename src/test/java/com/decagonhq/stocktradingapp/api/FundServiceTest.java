package com.decagonhq.stocktradingapp.api;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.decagonhq.stocktradingapp.api.model.Fund;
import com.decagonhq.stocktradingapp.api.repository.FundRepository;
import com.decagonhq.stocktradingapp.api.services.FundService;

@SpringBootTest
public class FundServiceTest {
	

	@Autowired
	private FundService fundService;
	
	@MockBean
	private FundRepository fundRepository;
	
	@Test
	public void getFundByUsersTest() {
		int id = 999;
		Date date = new Date();
		Timestamp created =new Timestamp(date.getTime());
		when(fundRepository.findAllByUserId(id)).thenReturn(Stream.of(
				new Fund(12.3, "new funds", created, 99),
				new Fund(12.3, "new funds", created, 99)
				).collect(Collectors.toList()));
	
		assertEquals(2, fundService.getAllFundsByUserId(id).size());
	}
	
	@Test
	public void addNewFundTest() {
		Date date = new Date();
		Timestamp created =new Timestamp(date.getTime());
		Fund fund = new Fund(100, "this is good", created, 1);
		when(fundRepository.save(fund)).thenReturn(fund);
		
		assertEquals(fund, fundService.addNewFund(fund));
	}
	
	@Test
	public void getFundByIdTest() {
		
		int id = 90;
		Optional<Fund> fund =  fundRepository.findById(id);
		when(fundRepository.findById(id)).thenReturn(fund);
		assertEquals(fund, fundService.getFundById(id));
	}
	
	

}
