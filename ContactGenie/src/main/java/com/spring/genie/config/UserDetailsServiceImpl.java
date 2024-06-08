package com.spring.genie.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.spring.genie.Dao.UserRepository;
import com.spring.genie.entities.User;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository uRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User u = uRepo.getUserByUserName(username);
		if (u == null) {
			throw new UsernameNotFoundException("user not found");
		}
		UserDetailImpl udi = new UserDetailImpl(u);
		return udi;
	}

}
