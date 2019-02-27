package com.synox.modeling.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synox.modeling.domain.Customer;
import com.synox.modeling.repositories.CustomerRepository;
import com.synox.modeling.services.exception.ObjectNotFoundException;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository repo;
	
	public Customer findById(Integer id) {
		Optional<Customer> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"No data found! Id: " + id + ", Type: " + Customer.class.getName()));
	}
}
