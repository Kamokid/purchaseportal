package com.cst8333.admin.product;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.cst8333.common.entity.Product;
import com.cst8333.common.entity.User;


public interface ProductRepository extends PagingAndSortingRepository<Product, Integer>, CrudRepository<Product, Integer> {

	@Query("UPDATE Product p SET p.enabled = ?2 WHERE p.id= ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);
	
	public Product findByName(String name);
	
	public Long countById(Integer id);
}
