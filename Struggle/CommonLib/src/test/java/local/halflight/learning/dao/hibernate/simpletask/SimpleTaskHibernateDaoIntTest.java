package local.halflight.learning.dao.hibernate.simpletask;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import local.halflight.learning.config.TransactionManagerConfiguration;
import local.halflight.learning.entity.simpletask.SimpleTaskDbEntity;
import local.halflight.learning.testutils.TestDataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:common-test-beans.xml" })
@Rollback
@Transactional(value = TransactionManagerConfiguration.HIBERNATE_TRANSACTION_MANAGER)
public class SimpleTaskHibernateDaoIntTest {
	// TODO extract abstract super

	private static final Logger LOG = LoggerFactory.getLogger(SimpleTaskHibernateDaoIntTest.class);

	private static final Long TEST_ID = 123L;

	@Autowired
	private SimpleTaskHibernateDao simpleTaskHibernateDao;

	SimpleTaskDbEntity entity;
	
	@Before
	public void before() {
		entity = TestDataSource.generateSimpleTaskDbEntity(TEST_ID);
		SimpleTaskDbEntity cleanup = simpleTaskHibernateDao.findByNameWithNamedQuery(entity.getTaskName());
		if (cleanup != null) {
			simpleTaskHibernateDao.delete(cleanup.getId());
		}
	}

	@After
	public void after() {
		clean();
	}

	@Test
	public void testCrud() throws Exception {
		SimpleTaskHibernateDao dao = getDao();
		SimpleTaskDbEntity dto = getDto();
		dto.setId(null);

		// save
		SimpleTaskDbEntity saved = dao.save(dto);
		assertTrue("Saved dto should be not null", saved != null);
		assertTrue("Saved dto id should be not null", saved.getId() != null);

		// read
		SimpleTaskDbEntity founded = dao.findById(saved.getId());
		assertThat(founded).isNotNull();
		assertThat(founded).isEqualToComparingOnlyGivenFields(saved, "id", "taskName", "taskDescription", "priority",
				"notes");

		// update
		modifyDto(founded);
		SimpleTaskDbEntity updated = dao.update(founded);
		assertThat(updated).isNotNull();

		// remove
		dao.delete(updated.getId());
	
		SimpleTaskDbEntity notFound = dao.findById(updated.getId());
		assertThat(notFound).isNull();

	}
	
	
	public void testGetProjection() {
		String qString = "select "
				+ "new local.halflight.learning.dto.simpletask.SimpleTaskProjection(s.taskname, s.task_description, s.priority) "
				+ "from SimpleTaskDbEntity s  ";
//		Query query = createQuery(qString);		
	}

	public void testCustomQueryFind() {
		// Query query = currentSession().createQuery("from SimpleTaskDbEntity
		// st where st.id = :id");
	}

	public void testEntityNamedQueryFind() {
		// Query query =
		// currentSession().getNamedQuery("findTaskByName").setParameter("taskName",
		// taskName);
	}

	protected SimpleTaskHibernateDao getDao() {
		return simpleTaskHibernateDao;
	}

	protected SimpleTaskDbEntity getDto() {
		return entity;
	}

	protected SimpleTaskDbEntity modifyDto(final SimpleTaskDbEntity dto) {
		dto.setTaskName("UpdatedName");
//		dto.getNotes().add("UpdatedNote");
		return dto;
	}
	
	protected void clean() {
		SimpleTaskDbEntity cleanup = simpleTaskHibernateDao.findByNameWithNamedQuery(entity.getTaskName());
		if (cleanup != null) {
			simpleTaskHibernateDao.delete(cleanup.getId());
		}
	}

}
