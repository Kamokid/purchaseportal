package com.cst8333.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.cst8333.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {

	@Autowired
	private RoleRepository repo;
	
	@Test
	public void testCreateFirstRole() {
		Role roleAdmin = Role.builder().name("Admin").description("Manage All").build();
		Role savedRole = repo.save(roleAdmin);
		assertThat(savedRole.getId()).isGreaterThan(0);
	};
	
	@Test
	public void testCreateCustomerRole() {
		Role roleCus = Role.builder().name("Customer").description("Wholesale Buyer").build();
		Role savedRole = repo.save(roleCus);
		assertThat(savedRole.getId()).isGreaterThan(0);
	};
	
	@Test
	public void testCreateRestRole() {
		Role roleSales = Role.builder().name("Sales Agent").description("Manage Product Price").build();
//		Role savedRole = repo.save(roleSales);
		
		Role roleFulfilment = Role.builder().name("Fulfilment Agent").description("Manage fulfilment, Manage Orders, Customer Support" ).build();
//		Role savedRole = repo.save(roleFulfilment);
		
		repo.saveAll(List.of(roleSales, roleFulfilment));
		
	};
}
