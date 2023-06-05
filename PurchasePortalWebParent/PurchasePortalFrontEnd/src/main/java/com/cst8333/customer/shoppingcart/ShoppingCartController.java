package com.cst8333.customer.shoppingcart;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cst8333.common.entity.CartItem;
import com.cst8333.common.entity.Customer;
import com.cst8333.customer.Utility;
import com.cst8333.customer.user.CustomerRepository;


@Controller
public class ShoppingCartController {
	
	@Autowired
	private ShoppingCartService  cartService;
	
	@Autowired 
	private CustomerRepository custRepo;
	
	@GetMapping("/cart")
	public String viewCart(Model model, HttpServletRequest request ) {
		
		Customer customer = getCustomer(request);
		List <CartItem> cartItems = cartService.listCartItems(customer);
		
		float estimatedTotal = 0.0F;
		for(CartItem item : cartItems) {
			estimatedTotal += item.getSubtotal();
		}
		
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("estimatedTotal", estimatedTotal);
		return "cart/shoppingcart";
	}
	
	
	private Customer getCustomer(HttpServletRequest request) {
		
		String email = Utility.getEmailOfAuthenticatedCustomer(request);
		
//		System.out.println(email);
		
		return custRepo.getCustomerByEmail(email);
		
	}
}
