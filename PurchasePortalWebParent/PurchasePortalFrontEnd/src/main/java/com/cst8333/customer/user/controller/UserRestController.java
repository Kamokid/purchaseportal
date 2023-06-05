package com.cst8333.customer.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cst8333.customer.user.UserService;

@RestController
public class UserRestController {

	@Autowired
	private UserService service;
	
	@PostMapping("/users/checkemail")
	public String checkDuplicateEmail(@Param("email") String email){
		return service.isEmailUnique(email) ? "OK" : "Duplicated";
	}
}

