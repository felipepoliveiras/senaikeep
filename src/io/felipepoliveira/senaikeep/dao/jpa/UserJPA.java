package io.felipepoliveira.senaikeep.dao.jpa;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import io.felipepoliveira.senaikeep.dao.UserDAO;
import io.felipepoliveira.senaikeep.models.UserModel;

@Repository(value = "userDAO")
@SuppressWarnings("unchecked")
public class UserJPA extends AbstractJPA<UserModel> implements UserDAO{

	@Override
	public String getEntityName() {
		return "User";
	}

	@Override
	public List<UserModel> searchByEmail(String email) {
		return this.search("email", email);
	}

	@Override
	public UserModel searchByEmailAndPassword(String email, String password) {
		String hql = "SELECT u FROM User u WHERE u.email = :email AND u.password = :password";
		Query query = session().createQuery(hql);
		
		query.setParameter("email", email);
		query.setParameter("password", password);
		
		List<UserModel> result = query.list();
		
		if(result.size() > 0) {
			return result.get(0);
		}else {
			return null;
		}
	}
}
