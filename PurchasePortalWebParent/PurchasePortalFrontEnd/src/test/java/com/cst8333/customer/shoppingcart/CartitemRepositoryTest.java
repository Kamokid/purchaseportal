package com.cst8333.customer.shoppingcart;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.cst8333.common.entity.CartItem;
import com.cst8333.common.entity.Customer;
import com.cst8333.common.entity.Product;

@DataJpaTest(showSql=false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CartitemRepositoryTest {

	@Autowired private CartitemRepository repo;
	
	@Autowired private  TestEntityManager entityManager;
	
	@Test
	public void testSaveItem() {
		Integer customerId= 43;
		Integer productId =13;
		
		Customer customer = entityManager.find(Customer.class, customerId);
		Product product = entityManager.find(Product.class, productId);
		
		CartItem newItem= CartItem.builder().build();
		newItem.setCustomer(customer);
		newItem.setProduct(product);
		newItem.setQuantity(1);
		
		CartItem savedItem = repo.save(newItem);
		assertThat(savedItem.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testSave2Item() {
		Integer customerId= 41;
		Integer productId =15;
		Integer productId2 =16;
		
		Customer customer = entityManager.find(Customer.class, customerId);
		Product product = entityManager.find(Product.class, productId);
		
		CartItem item1= CartItem.builder().build();
		item1.setCustomer(customer);
		item1.setProduct(product);
		item1.setQuantity(2);
		
		CartItem item2= CartItem.builder().build();
		item2.setCustomer(customer);
		item2.setProduct(new Product(productId2));
		item2.setQuantity(3);
		
		Iterable <CartItem> savedItems = repo.saveAll(List.of(item1,item2));
		assertThat(savedItems).size().isGreaterThan(0);
	}
	
	@Test
	public void testFindByCustomer() {
		Integer customerId=41;
		List <CartItem> cartItems = repo.findByCustomer(Customer.builder().id(customerId).build());
		cartItems.forEach(System.out::println);
		
		assertThat(cartItems.size()).isEqualTo(2);
		
	}
	
	@Test
	public void testFindByCustomerAndProduct() {
		Integer customerId = 41;
		Integer productId=15;
		
		CartItem item = repo.findByCustomerAndProduct(new Customer(customerId), new Product(productId));
		assertThat(item).isNotNull();	
		}
	
	@Test
	public void testUpdateQuantity() {
		Integer customerId = 41;
		Integer productId = 15;
		Integer quantity = 15;
		
		repo.updateQuantity(quantity, customerId, productId);
		
		CartItem item = repo.findByCustomerAndProduct(new Customer(customerId), new Product(productId));
		assertThat(item.getQuantity()).isEqualTo(15);
	}
	
	@Test
	public void testDeleteByCustomerAndProduct() {
		Integer customerId = 41;
		Integer productId=15;
		
		repo.deleteByCustomerAndProduct(customerId,productId);
		CartItem item = repo.findByCustomerAndProduct(new Customer(customerId), new Product(productId));
		
		assertThat(item).isNull();
	}
	
}
