package local.halflight.learning.dao.hibernate.simpletask;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import local.halflight.learning.concurrent.model.TransactionManagerConfiguration;
import local.halflight.learning.dao.hibernate.AbstractHibernateDao;
import local.halflight.learning.dto.hibernate.simpletask.SimpleTaskDbEntity;

@Repository
@Transactional(value = TransactionManagerConfiguration.HIBERNATE_TRANSACTION_MANAGER)
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SimpleTaskHibernateDao extends AbstractHibernateDao<SimpleTaskDbEntity> {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleTaskHibernateDao.class);

	@Autowired
	public SimpleTaskHibernateDao(SessionFactory sessionFactory) {
		super(sessionFactory);
		LOG.info("Construct hibernate {} sessionF:{}.", getClass().getName(), sessionFactory);
	}

	@Override
	public SimpleTaskDbEntity save(SimpleTaskDbEntity entity) {
		Number checkRes = (Number) currentSession().getNamedQuery(SimpleTaskDbEntity.CHECK_BY_NAME)
				.setString("taskname", entity.getTaskName()).uniqueResult();
		int resInt = checkRes.intValue();
		SimpleTaskDbEntity saved;
		if (checkRes != null && resInt == 0) {
			saved = super.save(entity);
			LOG.info("Saved {} :{}.", getEntityType().getName(), saved);
		} else {
			saved = null;
			LOG.info("Failed to save {}, taskname={} already exists in db", entity, entity.getTaskName());
		}
		return saved;
	}
	
	@Override
	public SimpleTaskDbEntity update(SimpleTaskDbEntity entity) {
		return super.update(entity);
	}
	

	@Override
	public SimpleTaskDbEntity findById(Number id) {
		SimpleTaskDbEntity found = super.findById(id);
		if(found != null) {
			Hibernate.initialize(found.getNotes());
		}
		LOG.info("Find {} :{}.", getEntityType().getName(), found);
		return found;
	}

	public SimpleTaskDbEntity findByIdWithQuery(Integer id) {
		Query query = currentSession().createQuery("from SimpleTaskDbEntity st where st.id = :id");
		query.setParameter("id", id);
		return (SimpleTaskDbEntity) query.uniqueResult();
	}

	public SimpleTaskDbEntity findByNameWithNamedQuery(String taskName) {
		Query query = currentSession().getNamedQuery(SimpleTaskDbEntity.FIND_BY_NAME).setParameter("taskname", taskName);
		return (SimpleTaskDbEntity) query.uniqueResult();
	}

}
