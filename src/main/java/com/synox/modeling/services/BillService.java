package com.synox.modeling.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.synox.modeling.domain.PaymentWithBill;

@Service
public class BillService {
	
	//Fake method to simulate billing process
	public void createBill(PaymentWithBill payment, Date dateOrder) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateOrder);
		cal.add(Calendar.DAY_OF_MONTH, 30);
		payment.setDueDate(cal.getTime());
	}

}
