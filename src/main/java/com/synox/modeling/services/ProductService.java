package com.synox.modeling.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.synox.modeling.domain.Category;
import com.synox.modeling.domain.Product;
import com.synox.modeling.repositories.CategoryRepository;
import com.synox.modeling.repositories.ProductRepository;
import com.synox.modeling.services.exception.DataIntegrityException;
import com.synox.modeling.services.exception.ObjectNotFoundException;

@Service
public class ProductService {

	@Autowired
	ProductRepository repo;
	
	@Autowired
	CategoryRepository categoryRepo;
	
	public Product findById(Integer id) {
		Optional<Product> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"No data found! Id: " + id + ", Type: " + Product.class.getName()));
	}

	public List<Product> findAll() {
		List<Product> list = repo.findAll();
		return list;
	}
	
	public Page<Product> findPage(Integer page, Integer size, String direction, String properties) {
		return repo.findAll(PageRequest.of(page, size, Direction.valueOf(direction), properties));
	}
	
	public Page<Product> search(String name, List<Integer> ids, Integer page, Integer size, String direction, String properties) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), properties);
		List<Category> categories = categoryRepo.findAllById(ids);
 		return repo.findDistinctByNameContainingAndCategoriesIn(name, categories, pageRequest); 
	}
	
	public Product insert(Product obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Product update(Product obj) {
		findById(obj.getId());
		return repo.save(obj);
	}
	
	public void delete(Integer id) {
		findById(id);
		try {
			repo.deleteById(id);
		} 
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Unable to delete Product referenced by Products");
		}
	}
	
}
