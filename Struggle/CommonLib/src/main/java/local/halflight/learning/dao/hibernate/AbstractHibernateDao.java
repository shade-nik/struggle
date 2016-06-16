package local.halflight.learning.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.NotYetImplementedException;
import org.hibernate.criterion.Restrictions;
import org.springframework.core.GenericTypeResolver;

import local.halflight.learning.dao.Dao;

public abstract class AbstractHibernateDao<T> implements Dao<T> {

	private final SessionFactory sessionFactory;
	
	protected Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public AbstractHibernateDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.clazz = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), AbstractHibernateDao.class);
	}
	
	protected Session currentSession()
	{
		return sessionFactory.getCurrentSession();
	}
	
	protected Class<T> getEntityType() {
		return clazz;
	}
	
	@Override
	public T save(T entity)
	{
		Number id = (Number) currentSession().save(entity);
		T saved = findById(id);
		return saved;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T update(T entity) {
		Session sess = currentSession();
		sess.saveOrUpdate(entity);
		Serializable id = sess.getIdentifier(entity);
		T updated = (T) sess.get(clazz, id);
		return updated;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T merge(T entity) {
		return (T) currentSession().merge(entity);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public T findById(Number id) {
		return (T) currentSession().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public T findByStringId(String id) {
		return (T) currentSession().createCriteria(clazz).add(Restrictions.eq("id", id)).list().get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() {
		return currentSession().createCriteria(clazz).list();
	}

	@Override
	public List<T> queryForList(String query, Object[] params) {
		throw new NotYetImplementedException();
	}

	@Override
	public void delete(Number id) {
		if(id != null) 
		{
			Session sess = currentSession();
			T entity = (T) sess.get(clazz, id);
			sess.delete(entity);
		}
	}

}
