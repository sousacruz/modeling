package com.synox.modeling.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.synox.modeling.domain.Category;
import com.synox.modeling.domain.Product;

@Repository
@Transactional(readOnly=true)
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	//@Query("SELECT DISTINCT p FROM Product p INNER JOIN p.categories c WHERE p.name LIKE %:name% AND c IN :categories")
	Page<Product> findDistinctByNameContainingAndCategoriesIn(
			@Param("name") String name, 
			@Param("categories") List<Category> categories, 
			Pageable pageRequest);

}
