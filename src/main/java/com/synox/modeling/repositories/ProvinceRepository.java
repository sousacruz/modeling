package com.synox.modeling.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synox.modeling.domain.Province;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {

}
