package com.cst8333.customer.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cst8333.common.entity.Product;
import com.cst8333.common.entity.User;
import com.cst8333.common.exception.ProductNotFoundException;
import com.cst8333.customer.security.PurchaseUserDetails;
import com.cst8333.customer.user.UserService;


@Controller
public class ProductController {
	
	@Autowired
	private ProductService prodService;
	
	@Autowired UserService userService;
	
	@GetMapping("/products")
	public String listAll(Model model) {
		List<Product> listProducts = prodService.listAll();
		model.addAttribute("listProducts", listProducts);
		return "products/products";
	}
	
	@GetMapping("/products/{productid}")
	public  String getProductDetails(@PathVariable(name = "productid") Integer id, Model model, 
			@AuthenticationPrincipal PurchaseUserDetails loggedUser, RedirectAttributes redirectattributes) {
		try {
			String email = loggedUser.getUsername();
			User user = userService.getByEmail(email);
			Product product = prodService.getProduct(id);
			model.addAttribute("user", user);
			model.addAttribute("product", product);
			model.addAttribute("pageTitle", product.getName());
			return "products/productdetails";
		} catch (ProductNotFoundException e) {
			redirectattributes.addFlashAttribute("message", e.getMessage());
			return "error/404";
		}
			
	}
	
}
