package com.cst8333.customer.shoppingcart;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cst8333.common.entity.CartItem;
import com.cst8333.common.entity.Customer;
import com.cst8333.common.entity.Product;
import com.cst8333.common.entity.User;
import com.cst8333.customer.product.ProductRepository;

@Service
@Transactional
public class ShoppingCartService {
	
	@Autowired private CartitemRepository cartRepo;
	
	@Autowired private ProductRepository prodRepo;
	
	public Integer addProduct(Integer quantity, Integer productId, Integer customerId) throws ShoppingCartException {
		Integer updatedQuantity = quantity;
		Product product = new Product(productId);
		Customer customer = Customer.builder().id(customerId).build();
		
		CartItem cartItem = cartRepo.findByCustomerAndProduct(customer, product);
		
		if(cartItem != null) {
			updatedQuantity = cartItem.getQuantity() + quantity;
			
			if(updatedQuantity > 20) {
				throw new ShoppingCartException("Could not add " + quantity + " more item(s)"
						+ " because there is already " + cartItem.getQuantity() +" items in the cart. Maximum"
								+ " allowed quantity is 20");
			}
		}else{
			cartItem = CartItem.builder().build();
			cartItem.setCustomer(customer);
			cartItem.setProduct(product);
		}
		
		cartItem.setQuantity(updatedQuantity);
		cartRepo.save(cartItem);
		return updatedQuantity;
	}

	public List<CartItem> listCartItems(Customer customer){
		return cartRepo.findByCustomer(customer);
	}
	
	public float updateQuantity(Integer quantity, Integer productId, Customer customer) {
		cartRepo.updateQuantity(quantity, customer.getId(), productId);
		Product product = prodRepo.findById(productId).get();
		float subTotal = product.getDiscountPrice() * quantity;
		return subTotal;
	}
	
	public void removeProduct(Integer productId, Customer customer) {
		cartRepo.deleteByCustomerAndProduct(customer.getId(), productId);
	}

}