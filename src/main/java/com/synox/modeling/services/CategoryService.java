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
import com.synox.modeling.dto.CategoryDTO;
import com.synox.modeling.repositories.CategoryRepository;
import com.synox.modeling.services.exception.DataIntegrityException;
import com.synox.modeling.services.exception.ObjectNotFoundException;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository repo;
	
	public Category findById(Integer id) {
		Optional<Category> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"No data found! Id: " + id + ", Type: " + Category.class.getName()));
	}

	public List<Category> findAll() {
		List<Category> list = repo.findAll();
		return list;
	}
	
	public Page<Category> findPage(Integer page, Integer size, String direction, String properties) {
		return repo.findAll(PageRequest.of(page, size, Direction.valueOf(direction), properties));
	}
	
	public Category insert(Category obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Category update(Category obj) {
		findById(obj.getId());
		return repo.save(obj);
	}
	
	public void delete(Integer id) {
		findById(id);
		try {
			repo.deleteById(id);
		} 
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Unable to delete Category referenced by Products");
		}
	}
	
	public Category fromDTO(CategoryDTO objDto) {
		return new Category(objDto.getId(), objDto.getName());
	}
	
}
