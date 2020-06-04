package com.decagonhq.stocktradingapp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.decagonhq.stocktradingapp.api.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUserName(String userName);

}
