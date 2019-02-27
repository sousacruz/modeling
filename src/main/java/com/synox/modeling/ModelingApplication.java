package com.synox.modeling;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.synox.modeling.domain.Category;
import com.synox.modeling.domain.City;
import com.synox.modeling.domain.Product;
import com.synox.modeling.domain.Province;
import com.synox.modeling.repositories.CategoryRepository;
import com.synox.modeling.repositories.CityRepository;
import com.synox.modeling.repositories.ProductRepository;
import com.synox.modeling.repositories.ProvinceRepository;

@SpringBootApplication
public class ModelingApplication implements CommandLineRunner {

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private ProvinceRepository provinceRepo;
	
	@Autowired
	private CityRepository cityRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(ModelingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Category cat1 = new Category(null, "Info");
		Category cat2 = new Category(null, "Office");
		
		Product p1 = new Product(null, "Computer", 2000.0);
		Product p2 = new Product(null, "Printer", 800.0);
		Product p3 = new Product(null, "Mouse", 80.0);
		
		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2));
		
		p1.getCategories().addAll(Arrays.asList(cat1));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2));
		p3.getCategories().addAll(Arrays.asList(cat1));
		
		categoryRepo.saveAll(Arrays.asList(cat1, cat2));
		productRepo.saveAll(Arrays.asList(p1, p2, p3));

		Province prov1 = new Province(null, "Texas", "TX");
		Province prov2 = new Province(null, "Florida", "FL");
		
		City c1 = new City(null, "Orlando", prov2);
		City c2 = new City(null, "Tampa", prov1);
		City c3 = new City(null, "Austin", prov1);
		
		prov1.getCities().addAll(Arrays.asList(c3));
		prov2.getCities().addAll(Arrays.asList(c2, c3));
		
		provinceRepo.saveAll(Arrays.asList(prov1, prov2));
		cityRepo.saveAll(Arrays.asList(c1, c2, c3));
	}

}
