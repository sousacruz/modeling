package com.synox.modeling.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synox.modeling.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
