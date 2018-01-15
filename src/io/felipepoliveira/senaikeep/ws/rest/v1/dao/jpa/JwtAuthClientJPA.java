package io.felipepoliveira.senaikeep.ws.rest.v1.dao.jpa;

import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import io.felipepoliveira.senaikeep.dao.jpa.AbstractJPA;
import io.felipepoliveira.senaikeep.ws.rest.v1.dao.JwtAuthClientDAO;
import io.felipepoliveira.senaikeep.ws.rest.v1.models.JwtAuthClientModel;

@Repository(value = "jwtAuthClientDAO")
@SuppressWarnings("unchecked")
public class JwtAuthClientJPA extends AbstractJPA<JwtAuthClientModel> implements JwtAuthClientDAO{

	@Override
	public String getEntityName() {
		return "JwtAuthClient";
	}

	@Override
	public JwtAuthClientModel authenticate(JwtAuthClientModel jwtAuthClient) {
		
		String hql = "SELECT j FROM JwtAuthClient j WHERE j.user.email = :email AND j.minimumRequiredDate <= :date";
		Query query = session().createQuery(hql);
		
		query.setParameter("email", jwtAuthClient.getUser().getEmail());
		query.setParameter("date", jwtAuthClient.getRefreshDate());
		
		List<JwtAuthClientModel> result = query.list();
		if (result.size() > 0) {
			return result.get(0);
		}else {
			return null;
		}
	}

}
