package com.decagonhq.stocktradingapp.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.decagonhq.stocktradingapp.api.model.Withdrawal;
import com.decagonhq.stocktradingapp.api.repository.WithdrawlResipotary;
import com.decagonhq.stocktradingapp.api.services.WithdrawalService;

@SpringBootTest
public class WithdrawalServiceTest {
	
	
	@Autowired
	private WithdrawalService withdrawalService;
	
	@MockBean
	private WithdrawlResipotary withdrawlResipotary;
	
	
	@Test
	public void getAllWithdrawalsByUserIdTest() {
		int userId = 99;
		Date date = new Date();
		Timestamp created =new Timestamp(date.getTime());
		when(withdrawlResipotary.findAllByUserId(userId)).thenReturn(Stream.of(
				new Withdrawal(12.34, "new withdrawal", created, 1, 12),
				new Withdrawal(200.34, "new withdrawal", created, 1, 12)
				
				).collect(Collectors.toList()));
		assertEquals(2, withdrawalService.getAllWithdrawalsByUserId(userId).size());
	}
	
	@Test
	public void addNewWithdrawalTest() {
		Date date = new Date();
		Timestamp created =new Timestamp(date.getTime());
		Withdrawal withdrawal = new Withdrawal(23.45, "new withdrawal", created, 1, 12);
		when(withdrawlResipotary.save(withdrawal)).thenReturn(withdrawal);
		
		assertEquals(withdrawal, withdrawalService.addNewWithdrawal(withdrawal));
	}
	
	

}
