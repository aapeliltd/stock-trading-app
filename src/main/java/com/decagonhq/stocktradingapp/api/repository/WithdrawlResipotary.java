package com.decagonhq.stocktradingapp.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.decagonhq.stocktradingapp.api.model.Withdrawal;

public interface WithdrawlResipotary extends JpaRepository<Withdrawal, Integer> {

	List<Withdrawal> findAllByUserId(int id);
}
