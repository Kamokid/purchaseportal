package com.cst8333.customer.shoppingcart;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cst8333.common.entity.Customer;
import com.cst8333.customer.Utility;
import com.cst8333.customer.user.CustomerRepository;

@RestController
public class ShoppingCartRestController {

	@Autowired
	private ShoppingCartService cartService;
	
	@Autowired 
	private CustomerRepository custRepo;
	
	@PostMapping("/cart/add/{productId}/{quantity}/{customerId}")
	public String addProductToCart(@PathVariable("productId") Integer productId, 
			@PathVariable("quantity") Integer quantity, @PathVariable("customerId") Integer customerId) {
		
		Integer updatedQuantity;
		try {
			updatedQuantity = cartService.addProduct(quantity, productId, customerId);
			return  updatedQuantity + " item(s) of this product were added to your cart";
		} catch (ShoppingCartException e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}
		
	}
	
	@PostMapping("/cart/update/{productId}/{quantity}")
	public String updateQuantityInCart(@PathVariable("productId") Integer productId, 
			@PathVariable("quantity") Integer quantity, HttpServletRequest request) {
		
		Customer customer = getCustomer(request);
		float subTotal = cartService.updateQuantity(quantity, productId, customer);
		return  String.valueOf(subTotal);
	}
	
	private Customer getCustomer(HttpServletRequest request) {
		
		String email = Utility.getEmailOfAuthenticatedCustomer(request);
		
		return custRepo.getCustomerByEmail(email);
	}
	
	@DeleteMapping("/cart/remove/{productId}")
	public String removeProduct(@PathVariable("productId") Integer productId, HttpServletRequest request) {
		Customer customer = getCustomer(request);
		cartService.removeProduct(productId, customer);
		return "The product has been removed successfully";
	}
	
}
