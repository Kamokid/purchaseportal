package com.cst8333.customer.user.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cst8333.common.entity.Customer;
import com.cst8333.common.entity.Role;
import com.cst8333.common.entity.User;
import com.cst8333.customer.user.UserService;

import lombok.extern.slf4j.Slf4j;


@SessionAttributes("user")
@Slf4j
@Controller
@RequestMapping ("/users" )
public class SignupController {
	
	@Autowired
	private UserService service;
	
	@ModelAttribute("user")
	public User user() {
		User user = User.builder().build();
		Role roleCustomer = Role.builder().id(4).build();
		user.addRole(roleCustomer);
	    return user;
	}
	
	
	@GetMapping ("/signup")
	public String getSignup(Model model,  @ModelAttribute("user") User user ) {
		
//		if (user == null) {
//			user = User.builder().build();
//			Role roleCustomer = Role.builder().id(4).name("Customer").build();
//			user.addRole(roleCustomer);
//			model.addAttribute("user", user);
//		}else {
//				model.addAttribute("user", user);	
//			}
			
		return "users/signup" ;
	}
	
	@PostMapping("/nextpage")
	public String conSignup(Model model, @Validated User user, BindingResult bindingResult) {
//		log.info(user.toString());
//		 Input check result
		if (bindingResult.hasErrors()) {
		// NG: Return to the user signup screen
//		sessionStatus.setComplete();	
		return getSignup(model, user);
		}
		
		Customer customer = Customer.builder().build();
		model.addAttribute("customer", customer);
		model.addAttribute("user", user);

	     log.info(user.toString());
	     log.info(customer.toString());
			
		return "users/signup2" ;
	
	}
	
     @GetMapping("")
	public String conSignupAfterFailure(Model model, @ModelAttribute Customer customer) {
//     log.info(user.toString());
//     log.info(customer.toString());
		
		return "users/signup2" ;
	
	}
	
	
	/** User signup process */
	@PostMapping ("/signup")
	public String postSignup(Model model, @Validated Customer customer, BindingResult bindingError, 
			@ModelAttribute("user") User user,  RedirectAttributes redirectattributes, SessionStatus sessionStatus) {
	// Redirect to login screen;
//		System.out.println("Are we there");

	if (bindingError.hasErrors()) {
			// NG: Return to the user signup screen
		System.out.println("Are we there");
		log.info(user.toString());
		return conSignupAfterFailure(model, customer);
	}	
	    log.info(user.toString());
		log.info(customer.toString());
		
		user.setCustomer(customer);
		customer.setUser(user);
		service.save(user);
		sessionStatus.setComplete();
		
		redirectattributes.addFlashAttribute("message","You have been successfully registered");
		return "redirect:/login" ;
	}

}
