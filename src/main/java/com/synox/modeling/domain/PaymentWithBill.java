package com.synox.modeling.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.synox.modeling.domain.enums.PaymentStatus;

@Entity
public class PaymentWithBill extends Payment {
	private static final long serialVersionUID = 1L;
	
	private Date dueDate;
	private Date paymentDate;
	
	public PaymentWithBill() {
	}

	public PaymentWithBill(Integer id, PaymentStatus status, PurchaseOrder order, Date dueDate, Date paymentDate) {
		super(id, status, order);
		this.dueDate = dueDate;
		this.paymentDate = paymentDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

}
