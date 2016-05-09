package local.halflight.learning.dao.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.NotYetImplementedException;
import org.hibernate.criterion.Restrictions;

import local.halflight.learning.dao.Dao;

public class AbstractHibernateDao<T> implements Dao<T> {

	private SessionFactory sessionFactory;
	
	private Class<T> clazz;
	
	public AbstractHibernateDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	protected Session currentSession()
	{
		return sessionFactory.getCurrentSession();
	}
	
	protected Class<T> getEntityType()
	{
		return clazz;
	}
	
	@SuppressWarnings("unchecked")
	public T save(T entity)
	{
//		Serializable id = currentSession().save(entity);
		return (T) currentSession().merge(entity);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T findById(Integer id) {
		return (T) currentSession().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findByStringId(String id) {
		return (T) currentSession().createCriteria(clazz).add(Restrictions.eq("id", id)).list().get(0);
	}

	@Override
	public T update(T dto) {
		throw new NotYetImplementedException();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> retrieveAll() {
		return currentSession().createCriteria(clazz).list();
	}

	@Override
	public List<T> queryForList(String query, Object[] params) {
		throw new NotYetImplementedException();
	}

	@Override
	public void delete(T dto) {
		currentSession().delete(dto);
	}

}
