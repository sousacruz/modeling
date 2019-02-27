package com.synox.modeling.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synox.modeling.domain.Category;
import com.synox.modeling.repositories.CategoryRepository;
import com.synox.modeling.services.exception.ObjectNotFoundException;

@Service
public class CustomerService {

	@Autowired
	CategoryRepository repo;
	
	public Category findById(Integer id) {
		Optional<Category> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"No data found! Id: " + id + ", Type: " + Category.class.getName()));
	}
}
