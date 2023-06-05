package com.cst8333.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.cst8333.common.entity.Role;
import com.cst8333.common.entity.User;

@DataJpaTest(showSql=false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
	private UserRepository repo;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateNewUserWithOneRole() {
//		Role roleAdmin = entityManager.find(Role.class,1);
		Role roleAdmin = Role.builder().id(1).build();
		User user = User.builder().email("Khalilmn@gmail.com").password("mulago").firstName("Khalil").lastName("Silim")
				.build();
		user.addRole(roleAdmin);
		User savedUser = repo.save(user);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateNewUser() {

		Role roleSales = Role.builder().id(2).build();
		Role rolefulfilment = Role.builder().id(3).build();
		User user = User.builder().email("Max@gmail.com").password("uganda").firstName("Max").lastName("Payne").build();
		user.addRole(roleSales);
		user.addRole(rolefulfilment);
		User savedUser = repo.save(user);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testListAllUsers() {

		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}

	@Test
	public void testGetUser() {

		User user = repo.findById(3).get();
		System.out.println(user);
		assertThat(user).isNotNull();
	}

	@Test
	public void testUpdateUser() {

		User user = repo.findById(2).get();
		user.setEnabled(true);
		user.setEmail("MaxP@gmail.com");
		repo.save(user);
	}

	@Test
	public void testUpdateUserRole() {

		User user = repo.findById(2).get();
		Role roleSales = Role.builder().id(2).build();
//		Role rolefulfilment = Role.builder().id(3).build();

//		user.getRoles().remove(rolefulfilment.getId().equals(3));
		
		Set <Role> roles =  user.getRoles();
		
		for (Role role  : roles) {
            if (role.getId().equals(3)) {
                roles.remove(role);
                break;
            }
        }
		
		user.addRole(roleSales);

		repo.save(user);

	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "Khalilmn@gmail.com";
		User user = repo.getUserByEmail(email);
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testCountById() {
		Integer id = 3;
		Long countById = repo.countById(id);
		
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	
	@Test
	public void testDisableUser() {
		Integer id = 19;
		repo.updateEnabledStatus(id, false);
	}
	
	@Test
	public void testEnableUser() {
		Integer id = 3;
		repo.updateEnabledStatus(id, true);
	}
	
	@Test
	public void testListFirstPage() {
		int pageNumber = 0;
		int pageSize = 4;
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page <User> page = repo.findAll(pageable);
		
		List <User> listUsers = page.getContent();
		listUsers.forEach(user -> System.out.println(user));
		assertThat(listUsers.size()).isEqualTo(pageSize);
	}
	
	@Test
	public void testSearchUsers() {
		String keyword = "khalil";
		
		int pageNumber = 0;
		int pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page <User> page = repo.findAll(keyword, pageable);
		
		List <User> listUsers = page.getContent();
		listUsers.forEach(user -> System.out.println(user));
		
		assertThat(listUsers.size()).isGreaterThan(0);
	}

}
