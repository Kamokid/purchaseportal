package com.cst8333.common.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="cart_items")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn
	private Customer customer;
	
	@ManyToOne
	@JoinColumn
	private Product product;
	
	private int quantity;
	
	@Transient
	public float getSubtotal() {
		return product.getDiscountPrice() * quantity;
	}
		
}
