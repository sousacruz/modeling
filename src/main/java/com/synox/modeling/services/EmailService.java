package com.synox.modeling.services;

import org.springframework.mail.SimpleMailMessage;

import com.synox.modeling.domain.Customer;
import com.synox.modeling.domain.PurchaseOrder;

public interface EmailService {

	void sendOrderConfirmationEmail(PurchaseOrder obj);

	void sendEmail(SimpleMailMessage msg);
	
	void sendNewPasswordEmail(Customer customer, String password);
}
