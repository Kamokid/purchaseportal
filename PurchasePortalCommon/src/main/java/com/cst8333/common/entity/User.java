package com.cst8333.common.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer Id;
	
	@Column(length = 128, nullable = false, unique=true)
	private String email;
	
	@Column(length = 64, nullable=false)
	private String password;
	
	@Column(name ="first_name", length = 45, nullable = false)
	private String firstName;
	
	@Column(name ="last_name", length = 45, nullable = false)
	private String lastName;
	
	@Column(length = 64)
	private String photos; 
	
	private boolean enabled;
	
	@ManyToMany
	@JoinTable(
			name = "user_role",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns= @JoinColumn(name="role_id")
			)
	@Builder.Default private  Set<Role> roles = new HashSet<>();
	
	public void addRole(Role role) {
		this.roles.add(role);
	}
}
