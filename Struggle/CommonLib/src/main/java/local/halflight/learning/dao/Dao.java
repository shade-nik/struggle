package local.halflight.learning.dao;

import java.io.Serializable;
import java.util.List;

public interface Dao<T> {
//TODO switch to Optional<T>
	/*
	 * if new entity - generate index and save
	 * if not new - update existing 
	 */
	public T save(T entity);
	
	public T update(T entity);
	
	public T merge (T entity);

	public void delete(Serializable id);
	
	public T findById(Serializable id);

	public List<T> getAll();

	public List<T> queryForList(String query, Object[] params);
}
