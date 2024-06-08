package com.spring.genie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.genie.Dao.UserRepository;
import com.spring.genie.entities.User;

@Service
public class MyService {

	@Autowired
	private UserRepository uRepo;

	public User saveUser(User u) {
		User result = uRepo.save(u);
		return result;
	}

}
