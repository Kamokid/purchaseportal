package com.cst8333.admin.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

	@Autowired
	private UserService service;
	
	@PostMapping("/user/checkemail")
	public String checkDuplicateEmail(@Param("id") Integer id, @Param("email") String email){
		return service.isEmailUnique(id,email) ? "OK" : "Duplicated";
	}
}

