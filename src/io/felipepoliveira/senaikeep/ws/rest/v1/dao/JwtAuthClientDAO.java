package io.felipepoliveira.senaikeep.ws.rest.v1.dao;

import io.felipepoliveira.senaikeep.dao.DAO;
import io.felipepoliveira.senaikeep.ws.rest.v1.models.JwtAuthClientModel;

public interface JwtAuthClientDAO extends DAO<JwtAuthClientModel>{
	
	public JwtAuthClientModel authenticate(JwtAuthClientModel jwtAuthClient);
	
}
