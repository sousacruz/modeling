package com.synox.modeling.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.synox.modeling.security.User;

public class UserService {

	public static User authenticated() {
		try {
			return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}
}