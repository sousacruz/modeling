package com.synox.modeling.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.synox.modeling.domain.Customer;
import com.synox.modeling.services.validation.CustomerUpdate;

@CustomerUpdate
public class CustomerDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message="This field is required")
	@Length(min=5, max=120, message="Length must be between 5 and 120 characters")
	private String name;
	
	@NotEmpty(message="This field is required")
	@Email(message="Invalid Email")
	private String email;

	public CustomerDTO() {
	}

	public CustomerDTO(Customer obj) {
		this.id = obj.getId();
		this.name = obj.getName();
		this.email = obj.getEmail();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
