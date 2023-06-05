package com.cst8333.customer.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cst8333.common.entity.Customer;
import com.cst8333.common.entity.User;


public interface CustomerRepository extends CrudRepository<Customer, Integer> {

	@Query("SELECT c FROM Customer c JOIN c.user u WHERE u.email = :email")
	public Customer getCustomerByEmail(@Param ("email") String email);
}
