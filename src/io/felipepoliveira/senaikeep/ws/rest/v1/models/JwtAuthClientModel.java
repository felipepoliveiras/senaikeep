package io.felipepoliveira.senaikeep.ws.rest.v1.models;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.felipepoliveira.senaikeep.models.UserModel;
import io.felipepoliveira.senaikeep.ws.rest.v1.utils.AuthClient;

@Entity(name = "JwtAuthClient")
@Table(name = "jwt_auth_1")
public class JwtAuthClientModel implements AuthClient{
	
	public JwtAuthClientModel() {}
	
	@Id
	@JsonIgnore
	private Long id;
	
	@PrimaryKeyJoinColumn(referencedColumnName = "id")
	@OneToOne(optional = false, targetEntity = UserModel.class)
	@JsonIgnore
	private UserModel user;
	
	@Transient
	private String token;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonIgnore
	private Date minimumRequiredDate;
	
	@Transient
	@JsonIgnore
	private Date refreshDate;
	
	public void generateToken(UserModel user) throws IllegalArgumentException, JWTCreationException, UnsupportedEncodingException {
		this.refreshDate = new Date();
		
		this.token = 
			JWT.create()
			.withSubject(user.getEmail())
			.withIssuedAt(refreshDate)
			.sign(Algorithm.HMAC512(user.getPassword()));
	}
	
	public void updateMinimumRequiredDate() {
		this.minimumRequiredDate = new Date();
	}
	
	@Override
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
		
		//Decode the token and populate the object with the data
		DecodedJWT decodedJwt = JWT.decode(token);
		
		//The jwt data
		this.refreshDate = decodedJwt.getIssuedAt();
		
		//The user data
		this.user = new UserModel();
		this.user.setEmail(decodedJwt.getSubject());
	}

	public Date getMinimumRequiredDate() {
		return minimumRequiredDate;
	}

	public void setMinimumRequiredDate(Date minimumRequiredDate) {
		this.minimumRequiredDate = minimumRequiredDate;
	}

	public Date getRefreshDate() {
		return refreshDate;
	}

	public void setRefreshDate(Date refreshDate) {
		this.refreshDate = refreshDate;
	}
}
