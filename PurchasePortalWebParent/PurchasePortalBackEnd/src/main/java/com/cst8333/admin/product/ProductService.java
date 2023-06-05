package com.cst8333.admin.product;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cst8333.common.entity.Product;
import com.cst8333.common.exception.ProductNotFoundException;


//import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

	@Autowired
	private ProductRepository repo;
	
	public List<Product> listAll(){
		return (List<Product>) repo.findAll();
	}
	
	public Product save(Product product) {
		if (product.getId() == null) {
			product.setCreatedTime(new Date());
		}
		 if(product.getAlias() == null || product.getAlias().isEmpty()) {
			 String defaultAlias = product.getName().replaceAll(" ", "-").toLowerCase();
			 product.setAlias(defaultAlias);
		 }else {
			 product.setAlias(product.getName().replaceAll(" ", "-").toLowerCase());
		 }
		 
		product.setUpdatedTime(new Date());
		Product updatedProduct = repo.save(product);
		return updatedProduct;
	}
	
	public void delete(Integer id) throws ProductNotFoundException {
		Long countById = repo.countById(id);
		if(countById == null || countById == 0) {
			throw new ProductNotFoundException("Could not find product with ID:" +id);
		}
		repo.deleteById(id);
	}
	
	public String productUnique(Integer id, String name) {
		boolean isCreatingNew = (id == null || id == 0);
		Product productByName = repo.findByName(name);
			
		if(isCreatingNew) {
			if(productByName != null) return "Duplicate";
		}else {
			if(productByName != null && productByName.getId() != id) {
				return "Duplicate";
			}
		}
		
		return "OK";
	}
		
		
	public void updateProductEnabledStatus(Integer id, boolean enabled) {
		repo.updateEnabledStatus(id, enabled);
	}
}
