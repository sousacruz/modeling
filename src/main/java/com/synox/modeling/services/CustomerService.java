package com.synox.modeling.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.synox.modeling.domain.Customer;
import com.synox.modeling.dto.CustomerDTO;
import com.synox.modeling.repositories.CustomerRepository;
import com.synox.modeling.services.exception.DataIntegrityException;
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
	
	public List<Customer> findAll() {
		List<Customer> list = repo.findAll();
		return list;
	}
	
	public Page<Customer> findPage(Integer page, Integer size, String direction, String properties) {
		return repo.findAll(PageRequest.of(page, size, Direction.valueOf(direction), properties));
	}
	
	public Customer insert(Customer obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Customer update(Customer obj) {
		Customer merged = findById(obj.getId());
		updateData(merged, obj);
		return repo.save(merged);
	}
	
	private void updateData(Customer merged, Customer obj) {
		merged.setName(obj.getName());
		merged.setEmail(obj.getEmail());
	}

	public void delete(Integer id) {
		findById(id);
		try {
			repo.deleteById(id);
		} 
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Unable to delete Customer referenced by Products");
		}
	}
	
	public Customer fromDTO(CustomerDTO objDto) {
		return new Customer(objDto.getId(), objDto.getName(), objDto.getEmail(), null);
	}

}
