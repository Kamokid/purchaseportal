package com.cst8333.customer.product;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cst8333.common.entity.Product;
import com.cst8333.common.exception.ProductNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repo;
	
	public List<Product> listAll(){
		return (List<Product>) repo.findAll();
	}
	
	public Product getProduct(Integer id) throws ProductNotFoundException {
//		Product product = repo.findById(id).get();
//		if (product == null) {
//			throw new ProductNotFoundException("Could not find product with ID:" +id);
//		}
//		 return product;
		
		try {
		return repo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new ProductNotFoundException("Could not find product with ID:" +id);
		}
	}
	

	
}
