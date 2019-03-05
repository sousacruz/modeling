package com.synox.modeling.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.synox.modeling.services.DBService;
import com.synox.modeling.services.EmailService;
import com.synox.modeling.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String ddlAutoStrategy;

	@Bean
	public boolean instantiateDatabase() throws ParseException {
		if (!"create".equals(ddlAutoStrategy)) {
			return false;
		}

		dbService.instantiateTestDatabase();
		return true;
	}
	

	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
}
