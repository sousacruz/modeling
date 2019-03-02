package com.synox.modeling.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.synox.modeling.domain.Customer;
import com.synox.modeling.domain.enums.CustomerType;
import com.synox.modeling.dto.CustomerNewDTO;
import com.synox.modeling.repositories.CustomerRepository;
import com.synox.modeling.services.exception.FieldMessage;

public class CustomerInsertValidator implements ConstraintValidator<CustomerInsert, CustomerNewDTO> {
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Override
	public void initialize(CustomerInsert ann) {
	}

	@Override
	public boolean isValid(CustomerNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> errors = new ArrayList<>();
		
		if (objDto.getCustomerType().equals(CustomerType.INDIVIDUAL.getCode()) && objDto.getDetails().equals("ERROR TEST")) {
			errors.add(new FieldMessage("Details", "Test passed"));
		}
		
		Customer customer = customerRepo.findByEmail(objDto.getEmail());
		if (customer != null) {
			errors.add(new FieldMessage("Email", "Email informed already in use, please check or inform a different one"));
		}
		
		for (FieldMessage e : errors) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
				.addPropertyNode(e.getFieldName())
				.addConstraintViolation();
		}
		return errors.isEmpty();
	}
}
