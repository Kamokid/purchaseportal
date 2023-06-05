package com.cst8333.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.cst8333.common.entity")
public class PurchasePortalFrontEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(PurchasePortalFrontEndApplication.class, args);
	}

}
