package com.synox.modeling.domain;

import javax.persistence.Entity;

import com.synox.modeling.domain.enums.PaymentStatus;

@Entity
public class PaymentWithCard extends Payment {
	private static final long serialVersionUID = 1L;

	private Integer installments;
	
	public PaymentWithCard() {
	}

	public PaymentWithCard(Integer id, PaymentStatus status, Order order, Integer installments) {
		super(id, status, order);
		this.installments = installments;
	}

	public Integer getInstallments() {
		return installments;
	}

	public void setInstallments(Integer installments) {
		this.installments = installments;
	}
	
}
