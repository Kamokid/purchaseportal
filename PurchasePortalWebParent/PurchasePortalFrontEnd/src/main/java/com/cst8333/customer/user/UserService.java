package com.cst8333.customer.user;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cst8333.common.entity.Customer;
import com.cst8333.common.entity.Product;
import com.cst8333.common.entity.Role;
import com.cst8333.common.entity.User;
import com.cst8333.common.exception.ProductNotFoundException;

@Service
@Transactional
public class UserService {
	
	public static final int USERS_PER_PAGE = 3;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired 
	private CustomerRepository custRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User getByEmail(String email) {
			return userRepo.getUserByEmail(email);
	}
	
	public Customer getCustomerByEmail(String email) {
		return custRepo.getCustomerByEmail(email);
	}
	
	public Customer getCustomer(Integer id) throws CustomerNotFoundException {
//		Product product = repo.findById(id).get();
//		if (product == null) {
//			throw new ProductNotFoundException("Could not find product with ID:" +id);
//		}
//		 return product;
		
		try {
		return custRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new CustomerNotFoundException("Could not find customer with ID");
		}
	}

	public void save(User user) {
		boolean isUpdatingUser = (user.getId() != null );
		
		if(isUpdatingUser) {
			User existingUser = userRepo.findById(user.getId()).get();
//			 User existingUser = userRepo.findById(user.getId()).orElseThrow(() -> new RuntimeException("User not found"));
//			
			if(user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			}else {
					encodePassword(user);
			}
		}else {
					encodePassword(user);
		}
		
		userRepo.save(user);

	}
	
	public User updateAccount(User userInForm) {
		User userInDB = userRepo.findById(userInForm.getId()).get();
		
		if(!userInForm.getPassword().isEmpty()) {
			userInDB.setPassword(userInForm.getPassword());
			encodePassword(userInDB);
		}
		
		userInDB.setFirstName(userInForm.getFirstName());
		userInDB.setLastName(userInForm.getLastName());
		
		return userRepo.save(userInDB);
	}
	
	
	private void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}

	public User get(Integer id) throws UserNotFoundException {
		try {
		return userRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new UserNotFoundException("Could not find user with ID:" +id);
		}
	}
	
	public void delete(Integer id) throws UserNotFoundException {
		Long countById = userRepo.countById(id);
		if(countById == null || countById == 0) {
			throw new UserNotFoundException("Could not find user with ID:" +id);
		}
		userRepo.deleteById(id);
	}
	
	public boolean isEmailUnique(String email) {
		User userByEmail = userRepo.getUserByEmail(email);
		
		if (userByEmail == null) {
			return true;
		}else {
			return false;
		}
		
	}

	
	
}
