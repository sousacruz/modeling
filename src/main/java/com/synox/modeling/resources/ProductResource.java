package com.synox.modeling.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synox.modeling.domain.Product;
import com.synox.modeling.dto.ProductDTO;
import com.synox.modeling.resources.utils.URL;
import com.synox.modeling.services.ProductService;

@RestController
@RequestMapping(value="/products")
public class ProductResource {
	
	@Autowired
	private ProductService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		Product obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProductDTO>> findPage(
			@RequestParam(value="name", defaultValue="") String name, 
			@RequestParam(value="categories", defaultValue="") String categories, 
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="size", defaultValue="24") Integer size,
			@RequestParam(value="direction", defaultValue="ASC") String direction,
			@RequestParam(value="properties", defaultValue="name") String properties
			) {
		String nameDecoded = URL.decodeStrParam(name);
		List<Integer> ids = URL.decodeIntList(categories);
		Page<Product> pagedList = service.search(nameDecoded, ids, page, size, direction, properties);
		Page<ProductDTO> pagedListDto = pagedList.map(obj -> new ProductDTO(obj));
		return ResponseEntity.ok().body(pagedListDto);
	}

}
