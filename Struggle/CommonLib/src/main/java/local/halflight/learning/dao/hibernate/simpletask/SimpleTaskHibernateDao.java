package local.halflight.learning.dao.hibernate.simpletask;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import local.halflight.learning.dao.hibernate.AbstractHibernateDao;
import local.halflight.learning.dto.simpletask.SimpleTaskDbEntity;

public class SimpleTaskHibernateDao extends AbstractHibernateDao<SimpleTaskDbEntity> {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleTaskHibernateDao.class);

	
	@Autowired
	public SimpleTaskHibernateDao(SessionFactory sessionFactory) {
		super(sessionFactory);

		LOG.info("Construct hibernate {} sessionF:{}.", getClass().getName(), sessionFactory);
	}

}
