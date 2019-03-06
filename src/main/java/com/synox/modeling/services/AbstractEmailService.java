package com.synox.modeling.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.synox.modeling.domain.Customer;
import com.synox.modeling.domain.PurchaseOrder;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Override
	public void sendOrderConfirmationEmail(PurchaseOrder obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(PurchaseOrder obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCustomer().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Order confirmed! Number: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}
	
	@Override
	public void sendNewPasswordEmail(Customer customer, String password) {
		SimpleMailMessage sm = prepareNewPasswordEmail(customer, password);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareNewPasswordEmail(Customer customer, String password) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(customer.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Reset password request");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("New password: " + password);
		return sm;
	}
}