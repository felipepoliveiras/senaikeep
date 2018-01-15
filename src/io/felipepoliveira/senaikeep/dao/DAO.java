package io.felipepoliveira.senaikeep.dao;

import java.util.List;

public interface DAO<T> {
	public List<T> all();
	
	public T search(Long id);
	
	public void delete(Long id);
	
	public void update(T obj);
	
	public void persist(T obj);
}
