package com.decagonhq.stocktradingapp.api.utility;

import org.springframework.stereotype.Service;



@Service
public class UserHeader {
	

	
	/*public User getUserFromRequestHeader(HttpServletRequest request) {
		User user = getUser(request);
		return user;
	}
	
	public double getUserBalance(HttpServletRequest request) {
		User user = getUser(request);
		java.util.List<Fund> debposits = fundRepository.findAllByUserId(user.getId());
		java.util.List<Withdrawal> withdrawals = withdrawalRepository.findAllByUserId(user.getId());
		if(debposits.size() > 0 || withdrawals.size() > 0) {
			double totalDeposit = 0;
			double totalWithdrawal = 0;
			for(Fund fund : debposits) {
				totalDeposit += fund.getAmount();
			}
			for(Withdrawal withdrawal : withdrawals) {
				totalWithdrawal += withdrawal.getAmount();
			}
			double total = totalDeposit - totalWithdrawal;
			return total;
		}
		return 0;
	}
	
	private User getUser(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		String token = authorizationHeader.substring(7); //count from Bearer to the space after.
		String userName = jsonWebUtility.extractUsername(token);
		
		User user = userRepository.findByUserName(userName);
		return user;
	}
	*/

}
