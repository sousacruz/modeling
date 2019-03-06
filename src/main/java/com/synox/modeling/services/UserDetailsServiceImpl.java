package com.synox.modeling.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.synox.modeling.domain.Customer;
import com.synox.modeling.repositories.CustomerRepository;
import com.synox.modeling.security.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private CustomerRepository repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Customer customer = repo.findByEmail(email);
		if (customer == null) {
			throw new UsernameNotFoundException(email);
		}
		return new User(customer.getId(), customer.getEmail(), customer.getPassword(), customer.getProfiles());
	}
}
