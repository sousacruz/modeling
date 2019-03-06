package com.synox.modeling.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.synox.modeling.domain.Customer;
import com.synox.modeling.repositories.CustomerRepository;
import com.synox.modeling.services.exception.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private EmailService emailService;

	private Random rand = new Random();

	public void sendNewPassword(String email) {

		Customer customer = customerRepo.findByEmail(email);
		if (customer == null) {
			throw new ObjectNotFoundException("Email not found");
		}

		String newPassword = newPassword();
		customer.setPassword(pe.encode(newPassword));

		customerRepo.save(customer);
		emailService.sendNewPasswordEmail(customer, newPassword);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i=0; i<10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		if (opt == 0) {      // digit
			return (char) (rand.nextInt(10) + 48);
		}
		else if (opt == 1) { // upper case letter
			return (char) (rand.nextInt(26) + 65);
		}
		else {               // lower case letter
			return (char) (rand.nextInt(26) + 97);
		}
	}
}