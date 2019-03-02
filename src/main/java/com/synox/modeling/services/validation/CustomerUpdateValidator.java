package com.synox.modeling.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.synox.modeling.domain.Customer;
import com.synox.modeling.dto.CustomerDTO;
import com.synox.modeling.repositories.CustomerRepository;
import com.synox.modeling.services.exception.FieldMessage;

public class CustomerUpdateValidator implements ConstraintValidator<CustomerUpdate, CustomerDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Override
	public void initialize(CustomerUpdate ann) {
	}

	@Override
	public boolean isValid(CustomerDTO objDto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> errors = new ArrayList<>();
		
		Customer customer = customerRepo.findByEmail(objDto.getEmail());
		if (customer != null && !customer.getId().equals(uriId)) {
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
