package com.synox.modeling.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synox.modeling.domain.PurchaseOrder;
import com.synox.modeling.repositories.PurchaseOrderRepository;
import com.synox.modeling.services.exception.ObjectNotFoundException;

@Service
public class PurchaseOrderService {

	@Autowired
	PurchaseOrderRepository repo;
	
	public PurchaseOrder findById(Integer id) {
		Optional<PurchaseOrder> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"No data found! Id: " + id + ", Type: " + PurchaseOrder.class.getName()));
	}
}
