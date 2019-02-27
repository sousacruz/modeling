package com.synox.modeling;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.synox.modeling.domain.Category;
import com.synox.modeling.repositories.CategoryRepository;

@SpringBootApplication
public class ModelingApplication implements CommandLineRunner {

	@Autowired
	private CategoryRepository categoryRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(ModelingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Category cat1 = new Category(null, "Info");
		Category cat2 = new Category(null, "Office");
		
		categoryRepo.saveAll(Arrays.asList(cat1, cat2));		
	}

}
