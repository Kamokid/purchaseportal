package com.cst8333.common.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

//import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode
@Entity
@Table(name="products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	
	
	
	public Product(Integer id) {
		super();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 256, nullable = false, unique=true)
	private String name;
	
	@Column(length = 256, nullable = false, unique=true)
	private String alias;
	
//	@Column(length = 512, nullable = false, name = "short_description")
//	private String shortDescription;
	
	@Column(length = 10000, nullable = false, name = "full_description")
	private String fullDescription;
	
	@Column(name = "main_image", nullable = false)
	private String mainImage;
	
	@Column(name = "created_time")
	private Date createdTime;
	
	@Column(name = "updated_time")
	private Date updatedTime;
	
	private boolean enabled;
	
	@Column(name = "in_stock")
	private boolean inStock;
	
	private float  cost;
	
	private float price;
	
	@Column(name = "discount_percent")
	private float discountPercent;
	
	private float length;
	private float width;
	private float height;
	private float weight;
	
	@EqualsAndHashCode.Exclude
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name="product_id", referencedColumnName="id")
	@Builder.Default private Set<ProductImage> images = new HashSet<>();
	
	public void addImage(String imageName) {
		this.images.add(new ProductImage (imageName, this));
	};
	
	@Transient
	public String getMainImagePath() {
		if(id == null || mainImage == null) return "/images/image-thumbnail.png";
		return "/product-images/" + this.id + "/" + this.mainImage;
	}
	
	@Transient
	public float getDiscountPrice() {
		
		return this.price * (1-(this.discountPercent/100));
	}
	

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Product other = (Product) obj;
//		return Objects.equals(alias, other.alias) && Float.floatToIntBits(cost) == Float.floatToIntBits(other.cost)
//				&& Objects.equals(createdTime, other.createdTime)
//				&& Float.floatToIntBits(discountPercent) == Float.floatToIntBits(other.discountPercent)
//				&& enabled == other.enabled && Objects.equals(fullDescription, other.fullDescription)
//				&& Float.floatToIntBits(height) == Float.floatToIntBits(other.height) && Objects.equals(id, other.id)
//				&& inStock == other.inStock && Float.floatToIntBits(length) == Float.floatToIntBits(other.length)
//				&& Objects.equals(mainImage, other.mainImage) && Objects.equals(name, other.name)
//				&& Float.floatToIntBits(price) == Float.floatToIntBits(other.price)
//				&& Objects.equals(updatedTime, other.updatedTime)
//				&& Float.floatToIntBits(weight) == Float.floatToIntBits(other.weight)
//				&& Float.floatToIntBits(width) == Float.floatToIntBits(other.width);
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(alias, cost, createdTime, discountPercent, enabled, fullDescription, height, id, inStock,
//				length, mainImage, name, price, updatedTime, weight, width);
//	}
}
