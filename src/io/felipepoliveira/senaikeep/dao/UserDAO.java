package io.felipepoliveira.senaikeep.dao;

import java.util.List;

import io.felipepoliveira.senaikeep.models.UserModel;

public interface UserDAO extends DAO<UserModel>{
	public List<UserModel> searchByEmail(String email);
	
	public UserModel searchByEmailAndPassword(String email, String password);
}
