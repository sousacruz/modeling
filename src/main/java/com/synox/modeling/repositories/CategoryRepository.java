package com.synox.modeling.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synox.modeling.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
