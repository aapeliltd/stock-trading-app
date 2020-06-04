package com.decagonhq.stocktradingapp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.decagonhq.stocktradingapp.api.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
