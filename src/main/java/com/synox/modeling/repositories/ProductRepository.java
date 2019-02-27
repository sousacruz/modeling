package com.synox.modeling.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synox.modeling.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
