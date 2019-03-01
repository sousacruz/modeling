package com.synox.modeling;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.synox.modeling.domain.Address;
import com.synox.modeling.domain.Category;
import com.synox.modeling.domain.City;
import com.synox.modeling.domain.Customer;
import com.synox.modeling.domain.Payment;
import com.synox.modeling.domain.PaymentWithBill;
import com.synox.modeling.domain.PaymentWithCard;
import com.synox.modeling.domain.Product;
import com.synox.modeling.domain.Province;
import com.synox.modeling.domain.PurchaseOrder;
import com.synox.modeling.domain.PurchaseOrderItem;
import com.synox.modeling.domain.enums.CustomerType;
import com.synox.modeling.domain.enums.PaymentStatus;
import com.synox.modeling.repositories.AddressRepository;
import com.synox.modeling.repositories.CategoryRepository;
import com.synox.modeling.repositories.CityRepository;
import com.synox.modeling.repositories.CustomerRepository;
import com.synox.modeling.repositories.PaymentRepository;
import com.synox.modeling.repositories.ProductRepository;
import com.synox.modeling.repositories.ProvinceRepository;
import com.synox.modeling.repositories.PurchaseOrderItemRepository;
import com.synox.modeling.repositories.PurchaseOrderRepository;

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
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private AddressRepository addressRepo;
	
	@Autowired
	private PurchaseOrderRepository purchaseOrderRepo;
	
	@Autowired
	private PurchaseOrderItemRepository purchaseOrderItemRepo;
	
	@Autowired
	private PaymentRepository paymentRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(ModelingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Category catInfo = new Category(null, "Info");
		Category catOffice = new Category(null, "Office");
		Category catBeauty = new Category(null, "Beauty");
		Category catBooks = new Category(null, "Books");
		Category catCamera = new Category(null, "Camera & Photo");
		Category catPhones = new Category(null, "Cell Phones & Accessories");
		Category catCoins = new Category(null, "Collectible Coins");
		Category catElectron = new Category(null, "Consumer Electronics");
		Category catEntertain = new Category(null, "Entertainment Collectibles");
		Category catArt = new Category(null, "Fine Art");
		Category catFood = new Category(null, "Grocery & Gourmet Food");
		Category catHealth = new Category(null, "Health & Personal Care");
		Category catHome = new Category(null, "Home & Garden");
		Category catDesign = new Category(null, "Independent Design");
		
		Product prodComputer = new Product(null, "Computer", 2000.0);
		Product prodPrinter = new Product(null, "Printer", 800.0);
		Product prodMouse = new Product(null, "Mouse", 80.0);
		
		catInfo.getProducts().addAll(Arrays.asList(prodComputer, prodPrinter, prodMouse));
		catOffice.getProducts().addAll(Arrays.asList(prodPrinter));
		
		prodComputer.getCategories().addAll(Arrays.asList(catInfo));
		prodPrinter.getCategories().addAll(Arrays.asList(catInfo, catOffice));
		prodMouse.getCategories().addAll(Arrays.asList(catInfo));
		
		categoryRepo.saveAll(Arrays.asList(catArt, catBeauty, catBooks, catCamera, catCoins, catDesign, catElectron, catEntertain, catFood, catHealth, catHome, catInfo, catOffice));
		productRepo.saveAll(Arrays.asList(prodComputer, prodPrinter, prodMouse));

		Province provinceTX = new Province(null, "Texas", "TX");
		Province provinceFL = new Province(null, "Florida", "FL");
		
		City cityOrlando = new City(null, "Orlando", provinceFL);
		City cityTampa = new City(null, "Tampa", provinceTX);
		City cityAustin = new City(null, "Austin", provinceTX);
		
		provinceTX.getCities().addAll(Arrays.asList(cityAustin));
		provinceFL.getCities().addAll(Arrays.asList(cityTampa, cityAustin));
		
		provinceRepo.saveAll(Arrays.asList(provinceTX, provinceFL));
		cityRepo.saveAll(Arrays.asList(cityOrlando, cityTampa, cityAustin));
		
		Customer custACC = new Customer(null, "ACC", "contact@austinacc.edu", CustomerType.COMPANY);
		Address addr78613 = new Address(null, "1555 Cypress Creek Rd", "Cedar Park", "78613", cityAustin, custACC);
		Address addr78640 = new Address(null, "1200 Kohiers Crossing", "Kyle", "78640", cityAustin, custACC);
		custACC.getAddresses().addAll(Arrays.asList(addr78613, addr78640));
		custACC.getPhones().addAll(Arrays.asList("+1 (512) 223-2000", "+1 (512) 262-6500"));
		
		customerRepo.save(custACC);
		addressRepo.saveAll(Arrays.asList(addr78613, addr78640));
		
		PurchaseOrder order78613 = new PurchaseOrder(null, sdf.parse("27/02/2019 11:47"), custACC, addr78613);
		PurchaseOrder order78640 = new PurchaseOrder(null, sdf.parse("28/02/2019 14:17"), custACC, addr78640);
		
		Payment pay78613 = new PaymentWithCard(null, PaymentStatus.PAID, order78613, 3);
		order78613.setPayment(pay78613);
		
		Payment pay78640 = new PaymentWithBill(null, PaymentStatus.OPEN, order78640, sdf.parse("20/10/2019 00:00"), null);
		order78640.setPayment(pay78640);
		
		custACC.getOrders().addAll(Arrays.asList(order78613, order78640));
		
		purchaseOrderRepo.saveAll(Arrays.asList(order78613, order78640));
		paymentRepo.saveAll(Arrays.asList(pay78613, pay78640));
		
		PurchaseOrderItem item78613A = new PurchaseOrderItem(order78613, prodComputer, 0.0, 3.0, 2000.0);
		PurchaseOrderItem item78613B = new PurchaseOrderItem(order78613, prodMouse, 0.0, 2.0, 80.0);
		PurchaseOrderItem item78640A = new PurchaseOrderItem(order78640, prodPrinter, 100.0, 1.0, 800.0);
		
		order78613.getItens().addAll(Arrays.asList(item78613A, item78613B));
		order78640.getItens().addAll(Arrays.asList(item78640A));
		
		prodComputer.getItens().addAll(Arrays.asList(item78613A));
		prodMouse.getItens().addAll(Arrays.asList(item78613B));
		prodPrinter.getItens().addAll(Arrays.asList(item78640A));
		
		purchaseOrderItemRepo.saveAll(Arrays.asList(item78613A, item78613B, item78640A));
		
	}

}
