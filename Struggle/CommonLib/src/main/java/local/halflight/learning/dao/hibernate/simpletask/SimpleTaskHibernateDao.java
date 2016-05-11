package local.halflight.learning.dao.hibernate.simpletask;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;

import local.halflight.learning.dao.Dao;
import local.halflight.learning.dao.hibernate.AbstractHibernateDao;
import local.halflight.learning.dto.hibernate.simpletask.SimpleTaskDbEntity;

@Repository
@Transactional
@Scope( proxyMode = ScopedProxyMode.TARGET_CLASS )
public class SimpleTaskHibernateDao extends AbstractHibernateDao<SimpleTaskDbEntity>{

	private static final Logger LOG = LoggerFactory.getLogger(SimpleTaskHibernateDao.class);

	@Autowired
	public SimpleTaskHibernateDao(SessionFactory sessionFactory) {
		super(sessionFactory);
		LOG.info("Construct hibernate {} sessionF:{}.", getClass().getName(), sessionFactory);
	}

	@Override
	public SimpleTaskDbEntity save(SimpleTaskDbEntity entity) {
		SimpleTaskDbEntity saved = super.save(entity);
		LOG.info("Saved {} :{}.", getEntityType().getName(), saved);
		return saved;
	}

	@Override
	public SimpleTaskDbEntity findById(Integer id) {
		SimpleTaskDbEntity found = super.findById(id);
		LOG.info("Find {} :{}.", getEntityType().getName(), found);
		return found;
	}
	
	public SimpleTaskDbEntity findByIdWithQuery(Integer id) {
		Query query = currentSession().createQuery("from SimpleTaskDbEntity st where st.id = :id");
		query.setParameter("id", id);
		return (SimpleTaskDbEntity) query.uniqueResult();
	}

	public SimpleTaskDbEntity findByNameWithNamedQuery(String taskName) {
		Query query = currentSession().getNamedQuery("findTaskByName").setParameter("taskName", taskName);
		return (SimpleTaskDbEntity) query.uniqueResult();
	}
	
}
