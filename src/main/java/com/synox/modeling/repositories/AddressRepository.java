package com.synox.modeling.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synox.modeling.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
