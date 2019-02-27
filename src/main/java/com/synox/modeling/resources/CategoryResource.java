package com.synox.modeling.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.synox.modeling.domain.Category;

@RestController
@RequestMapping(value="/categories")
public class CategoryResource {
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Category> listAll() {
		Category cat1 = new Category(1, "Info");
		Category cat2 = new Category(2, "Office");
		
		List<Category> list = new ArrayList<>();
		
		list.addAll(Arrays.asList(cat1, cat2));
		return list;
	}

}
