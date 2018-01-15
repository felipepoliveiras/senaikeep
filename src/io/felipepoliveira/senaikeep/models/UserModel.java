package io.felipepoliveira.senaikeep.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.felipepoliveira.senaikeep.utils.Hasher;

@Entity(name = "User")
@Table(name = "user")
public class UserModel {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(length = 40, nullable = false, unique = false)
	@Size(min = 1, max = 40)
	@NotNull
	private String name;
	
	@Column(length = 120, nullable = false, unique = false)
	@Email
	@Size(max = 120)
	@NotNull
	private String email;
	
	@Column(length = 64, nullable = false, unique = false)
	@Size(min = 6, max = 64)
	@NotNull
	private String password;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void hashPassword() {
		this.password = Hasher.hashToMd5(this.password);
	}

}
