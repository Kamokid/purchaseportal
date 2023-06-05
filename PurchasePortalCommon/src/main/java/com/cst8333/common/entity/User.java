package com.cst8333.common.entity;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

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
	private Integer id;
	
	@Email
	@NotBlank
	@Column(length = 128, nullable = false, unique=true)
	private String email;
	
	@NotBlank
	@Length (min = 8, max = 100)
	@Pattern (regexp = "^[a-zA-Z0-9$.@_/]+$" )
	@Column(length = 64, nullable=false)
	private String password;
	
	@NotBlank
	@Length (min = 2, max = 50)
	@Column(name ="first_name", length = 45, nullable = false)
	private String firstName;
	
	@NotBlank
	@Length (min = 2, max = 50)
	@Column(name ="last_name", length = 45, nullable = false)
	private String lastName;
	
	@Column(length = 64)
	private String photos; 
	
	private boolean enabled;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "user_role",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns= @JoinColumn(name="role_id")
			)
	@Builder.Default private  Set<Role> roles = new HashSet<>();
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
	private Customer customer;
	
	public void addRole(Role role) {
		this.roles.add(role);
	}
	
	@Transient
	public String getFullName() {
		return firstName + " " +lastName;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName+ ", lastName=" + lastName + ", enabled=" + enabled + ", roles=" + roles
				+ "]";
	}
	
	
}
