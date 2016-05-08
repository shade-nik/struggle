package local.halflight.learning.dao;

import java.util.List;

public interface Dao<T> {

	public T findById(Integer id);

	public T findByStringId(String id);

	public T update(T dto);

	public void delete(T dto);

	public List<T> retrieveAll();

	public List<T> queryForList(String query, Object[] params);
}
