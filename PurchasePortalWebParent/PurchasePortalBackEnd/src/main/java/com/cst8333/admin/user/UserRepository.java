package com.cst8333.admin.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cst8333.common.entity.User;


public interface UserRepository extends CrudRepository<User, Integer> {
	
	@Query("SELECT u from User u where u.email = :email")
	public User getUserByEmail(@Param ("email") String email);
}
