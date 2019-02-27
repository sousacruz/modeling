package com.synox.modeling.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synox.modeling.domain.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

}
