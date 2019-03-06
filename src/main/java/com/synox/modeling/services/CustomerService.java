package com.synox.modeling.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synox.modeling.domain.Address;
import com.synox.modeling.domain.City;
import com.synox.modeling.domain.Customer;
import com.synox.modeling.domain.enums.CustomerType;
import com.synox.modeling.domain.enums.UserProfile;
import com.synox.modeling.dto.CustomerDTO;
import com.synox.modeling.dto.CustomerNewDTO;
import com.synox.modeling.repositories.AddressRepository;
import com.synox.modeling.repositories.CityRepository;
import com.synox.modeling.repositories.CustomerRepository;
import com.synox.modeling.security.User;
import com.synox.modeling.services.exception.AuthorizationException;
import com.synox.modeling.services.exception.DataIntegrityException;
import com.synox.modeling.services.exception.ObjectNotFoundException;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository repo;
	
	@Autowired
	CityRepository cityRepo;
	
	@Autowired
	AddressRepository addressRepo;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public Customer findById(Integer id) {
		
		User user = UserService.authenticated();
		if (user==null || !user.hasRole(UserProfile.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acess denied");
		}
		
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
	
	@Transactional
	public Customer insert(Customer obj) {
		obj.setId(null);
		repo.save(obj);
		addressRepo.saveAll(obj.getAddresses());
		return obj;
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
		return new Customer(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null);
	}

	public Customer fromDTO(CustomerNewDTO objDto) {
		Customer customer = new Customer(null, objDto.getName(), objDto.getEmail(), CustomerType.toEnum(objDto.getCustomerType()), pe.encode(objDto.getPassword()));
		System.out.println(objDto.getCityId());
		City city = cityRepo.findById(objDto.getCityId()).orElse(null);
		Address addr = new Address(null, objDto.getAddress(), objDto.getDetails(), objDto.getZipcode(), city, customer);
		customer.getAddresses().add(addr);
		customer.getPhones().add(objDto.getPhone1());
		if (objDto.getPhone2() != null) {
			customer.getPhones().add(objDto.getPhone2());
		}
		if (objDto.getPhone3() != null) {
			customer.getPhones().add(objDto.getPhone3());
		}
		return customer;
	}
}
