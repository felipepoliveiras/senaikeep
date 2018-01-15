package io.felipepoliveira.senaikeep.dao.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import io.felipepoliveira.senaikeep.dao.DAO;

@SuppressWarnings("unchecked")
@Transactional
public abstract class AbstractJPA<T> implements DAO<T> {
	
	@Autowired
	private SessionFactory session;
	
	/**
	 * @return The current opened session
	 */
	protected Session session() {
		return session.getCurrentSession();
	}
	
	public abstract String getEntityName();
	
	@Override
	public List<T> all() {
		String hql = "SELECT o FROM "+getEntityName()+" o";
		Query query = session().createQuery(hql);
		
		return query.list();
	}

	@Override
	public T search(Long id) {
		String hql = "SELECT o FROM "+getEntityName()+" o WHERE o.id = :id";
		Query query = session().createQuery(hql);
		query.setParameter("id", id);
		
		List<T> result = query.list();
		if(result.size() > 0) {
			return result.get(0);
		}else {
			return null;
		}
	}
	
	public List<T> search(String field, Object value){
		Map<String, Object> map = new HashMap<>();
		map.put(field, value);
		
		return this.search(map);
	}
	

	public List<T> search(Map<String, Object> queryFields) {
		String hql = "SELECT o FROM " + getEntityName() + " o WHERE ";
		
		int mapSize = queryFields.size();
		int currentIndex = 0;
		
		//Get each field from the queried fields
		for (String field : queryFields.keySet()) {
			
			//Add the o.field = :field command
			hql += "o." + field + " = :" + field;
			
			//Add the OR to concat with another queried field
			if(currentIndex < mapSize - 1) {
				hql += " OR ";
			}
		}
		
		//Build the query and define the parameters with the values of the map
		Query query = session().createQuery(hql);
		for(String field : queryFields.keySet()) {
			query.setParameter(field, queryFields.get(field));
		}
		
		//Return the result
		return query.list();
	}
	
	@Override
	public void delete(Long id) {
		T queriedObject = this.search(id);
		
		//Delete only if the search method returns a result
		if(queriedObject != null) {
			session().delete(queriedObject);
		}
	}
	
	@Override
	public void persist(T obj) {
		session().persist(obj);
	}
	
	@Override
	public void update(T obj) {
		session().update(obj);
	}

}
