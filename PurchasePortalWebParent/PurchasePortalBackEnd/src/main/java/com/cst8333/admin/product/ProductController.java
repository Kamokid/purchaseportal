package com.cst8333.admin.product;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cst8333.admin.FileUploadUtil;
import com.cst8333.admin.user.UserNotFoundException;
import com.cst8333.common.entity.Product;
import com.cst8333.common.entity.User;
import com.cst8333.common.exception.ProductNotFoundException;

@Controller
public class ProductController {

	@Autowired
	private ProductService prodService;
	
	@GetMapping("/products")
	public String listAll(Model model) {
		List<Product> listProducts = prodService.listAll();
		model.addAttribute("listProducts", listProducts);
		return "products/products";
	}
	
	@GetMapping("/products/new")
	public String newProduct(Model model) {
		Product product = Product.builder().build();	
		product.setEnabled(true);
		product.setInStock(true);
		model.addAttribute("product", product);
		model.addAttribute("pageTitle", "Create New Product");
		return "products/productform";
	}
	
	@PostMapping("/products/save")
	public String saveProduct(Product product, @RequestParam("fileImage") MultipartFile multipartFile, 
			RedirectAttributes redirectattributes) throws IOException {
//		String description = product.getFullDescription().replaceAll("\\s*<div>", "<div>");
//		product.setFullDescription(description);
//		System.out.println(product.getFullDescription());
//		int count = product.getFullDescription().length();
//		System.out.println("Number of characters: " + count);
//		product.setFullDescription("test");
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			product.setMainImage(fileName);
			
			Product savedProduct = prodService.save(product);
			String uploadDir = "C:/Users/Khalil/CST833/PurchasePortal/PurchasePortalWebParent/product-images/" + savedProduct.getId();
			
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
		prodService.save(product);
		}
		redirectattributes.addFlashAttribute("message","The product has been saved succesfully");
		
		return "redirect:/products";
	}
	
	
	@GetMapping("/products/delete/{productid}")
	public  String deleteUser(@PathVariable(name = "productid") Integer id, Model model, RedirectAttributes redirectattributes) {
		try {
			prodService.delete(id);
			redirectattributes.addFlashAttribute("message","The product ID: " + id + " been deleted succesfully");
		} catch (ProductNotFoundException e) {
			redirectattributes.addFlashAttribute("message",e.getMessage());
		}
			return "redirect:/products";
	}
	
	@GetMapping("/products/{productid}/enabled/{status}")
	public String updateUserEnabledStatus(@PathVariable(name="productid") Integer id, @PathVariable(name="status") boolean enabled,
			RedirectAttributes redirectattributes) {
		prodService.updateProductEnabledStatus(id, enabled);
		String status = enabled ? "enabled": "disabled";
		String message = "The product ID " + id + " has been " +status;
		redirectattributes.addFlashAttribute("message",message);
		
		return "redirect:/products";
	}
}
