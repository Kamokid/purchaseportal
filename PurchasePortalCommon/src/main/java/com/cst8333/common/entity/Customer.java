package com.cst8333.common.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "customers")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	
	public Customer(Integer id) {
		super();
		this.id = id;
	}

	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	@Column(length = 128, nullable = false, unique=true)
	private String companyName;
	
	@Column(length = 15, nullable = false, unique=true)
	private String phoneNumber;
	
	@NotBlank
	@Column(length = 250, nullable = false, unique=true)
	private String address;
	
	@NotBlank
	@Column(length = 10, nullable = false)
	private String city;
	
	@NotBlank
	@Column(length = 10, nullable = false, unique=true)
	private String poBox;
	
	@OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
	private User user;
}
