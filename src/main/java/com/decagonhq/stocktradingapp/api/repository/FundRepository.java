package com.decagonhq.stocktradingapp.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.decagonhq.stocktradingapp.api.model.Fund;

public interface FundRepository extends JpaRepository<Fund, Integer> {

	List<Fund> findAllByUserId(int id);
}
