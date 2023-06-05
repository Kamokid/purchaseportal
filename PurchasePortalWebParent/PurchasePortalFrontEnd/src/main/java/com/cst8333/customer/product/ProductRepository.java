package com.cst8333.customer.product;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cst8333.common.entity.Product;


public interface ProductRepository extends PagingAndSortingRepository<Product, Integer>, CrudRepository<Product, Integer> {

	public Product findByName(String name);
	
	public Long countById(Integer id);
		
}
