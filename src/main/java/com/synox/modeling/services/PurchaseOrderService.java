package com.synox.modeling.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synox.modeling.domain.Payment;
import com.synox.modeling.domain.PaymentWithBill;
import com.synox.modeling.domain.PurchaseOrder;
import com.synox.modeling.domain.PurchaseOrderItem;
import com.synox.modeling.domain.enums.PaymentStatus;
import com.synox.modeling.repositories.PaymentRepository;
import com.synox.modeling.repositories.ProductRepository;
import com.synox.modeling.repositories.PurchaseOrderItemRepository;
import com.synox.modeling.repositories.PurchaseOrderRepository;
import com.synox.modeling.services.exception.ObjectNotFoundException;

@Service
public class PurchaseOrderService {

	@Autowired
	private PurchaseOrderRepository repo;
	
	@Autowired
	private PaymentRepository paymentRepo;
	
	@Autowired
	private PurchaseOrderItemRepository purchaseOrderItemRepo;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private BillService bill;
	
	public PurchaseOrder findById(Integer id) {
		Optional<PurchaseOrder> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"No data found! Id: " + id + ", Type: " + PurchaseOrder.class.getName()));
	}
	
	@Transactional
	public PurchaseOrder insert(PurchaseOrder obj) {
		obj.setId(null);
		obj.setInstant(new Date());
		obj.getPayment().setStatus(PaymentStatus.OPEN);
		obj.getPayment().setOrder(obj);
		
		if (obj.getPayment() instanceof PaymentWithBill) {
			PaymentWithBill payment = (PaymentWithBill) obj.getPayment();
			bill.createBill(payment, obj.getInstant());
		}
		
		obj = repo.save(obj);
		paymentRepo.save(obj.getPayment());
		for (PurchaseOrderItem i : obj.getItens()) {
			i.setDiscount(0.0);
			i.setPrice(productService.findById(i.getProduct().getId()).getPrice());
			i.setPurchaseOrder(obj);
		}
		purchaseOrderItemRepo.saveAll(obj.getItens());
		return obj;
	}
}
