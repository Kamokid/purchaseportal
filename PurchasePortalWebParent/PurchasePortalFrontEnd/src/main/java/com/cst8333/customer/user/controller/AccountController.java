package com.cst8333.customer.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cst8333.common.entity.User;
import com.cst8333.customer.security.PurchaseUserDetails;
import com.cst8333.customer.user.UserService;

@Controller
public class AccountController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("/account")
	public String viewDetails(@AuthenticationPrincipal PurchaseUserDetails loggedUser, Model model) {
		String email = loggedUser.getUsername();
		User user = service.getByEmail(email);
		model.addAttribute("user", user);
		return "users/accountform";
	}
	
	@PostMapping("/account/update")
	public String saveDetails(User user, @AuthenticationPrincipal PurchaseUserDetails loggedUser,
			RedirectAttributes redirectattributes) {
		System.out.println(user);
		service.updateAccount(user);
		loggedUser.setFirstName(user.getFirstName());
		loggedUser.setLastName(user.getLastName());
		redirectattributes.addFlashAttribute("message","Account details have been saved succesfully");
		
		return "redirect:/account";
	}

}
