package com.cst8333.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.cst8333.common.entity", "com.cst8333.admin.user"})
public class PurchasePortalBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(PurchasePortalBackEndApplication.class, args);
	}

}
