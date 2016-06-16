package local.halflight.learning.dao;

import java.util.List;

public interface Dao<T> {

	public T save(T entity);
	
	public T update(T entity);
	
	public T merge (T entity);

	public void delete(Number id);
	
	public T findById(Number id);

	public List<T> getAll();

	public List<T> queryForList(String query, Object[] params);
}
