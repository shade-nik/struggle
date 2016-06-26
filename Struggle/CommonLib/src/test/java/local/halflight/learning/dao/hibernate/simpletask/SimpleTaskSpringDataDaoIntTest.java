package local.halflight.learning.dao.hibernate.simpletask;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import local.halflight.learning.config.TransactionManagerConfiguration;
import local.halflight.learning.dao.springdatajpa.SimpleTaskSpringDataDao;
import local.halflight.learning.entity.simpletask.SimpleTaskDbEntity;
import local.halflight.learning.testutils.TestDataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:common-test-beans.xml" })
@Rollback
//@TransactionConfiguration
//@Transactional(value = TransactionManagerConfiguration.JPA_TRANSACTION_MANAGER)
public class SimpleTaskSpringDataDaoIntTest {
	// Not working yet

	private static final Logger LOG = LoggerFactory.getLogger(SimpleTaskSpringDataDaoIntTest.class);

	private static final Long TEST_ID = 123L;

	@Autowired
	private SimpleTaskSpringDataDao dao;

	SimpleTaskDbEntity entity;

	public SimpleTaskSpringDataDaoIntTest() {
		LOG.info("Constructor.");
	}
	
	@Before
	public void before() {
		entity = TestDataSource.generateSimpleTaskDbEntity(TEST_ID);
		// SimpleTaskDbEntity cleanup =
		// dao.findByTaskName(entity.getTaskName());
		// if (cleanup != null) {
		// dao.delete(cleanup.getId());
		// }
	}

	@After
	public void after() {
	}
	

	@Test
	public void testFindByName() {
		SimpleTaskSpringDataDao dao = getDao();
		SimpleTaskDbEntity dto = getDto();	
		dto.setId(null);

		SimpleTaskDbEntity saved = dao.save(dto);

		List<SimpleTaskDbEntity> tasks = dao.findByTaskName(saved.getTaskName());
		SimpleTaskDbEntity founded = contains(tasks, saved.getId());
		LOG.info("Found saved entity {}", founded);
		assertThat(founded).isNotNull();
		assertThat(founded).isEqualToComparingOnlyGivenFields(saved, "id", "taskName", "taskDescription", "priority");
		assertThat(founded.getNotes()).containsAll(saved.getNotes());
	}
	

	@Test
	public void testCrud() throws Exception {
		SimpleTaskSpringDataDao dao = getDao();
		SimpleTaskDbEntity dto = getDto();
		dto.setId(null);

		// save
		SimpleTaskDbEntity saved = dao.save(dto);
		assertTrue("Saved dto should be not null", saved != null);
		assertTrue("Saved dto id should be not null", saved.getId() != null);

		// read
		SimpleTaskDbEntity founded = dao.findOne(saved.getId());
		assertThat(founded).isNotNull();
		assertThat(founded).isEqualToComparingOnlyGivenFields(saved, "id", "taskName", "taskDescription", "priority");
		assertThat(founded.getNotes()).containsAll(saved.getNotes());

		// update
		modifyDto(founded);
		SimpleTaskDbEntity updated = dao.save(founded);
		assertThat(updated).isNotNull();

		// remove
		dao.delete(updated.getId());

		SimpleTaskDbEntity notFound = dao.findOne(updated.getId());
		assertThat(notFound).isNull();

	}

	public void testGetProjection() {
		String qString = "select "
				+ "new local.halflight.learning.dto.simpletask.SimpleTaskProjection(s.taskname, s.task_description, s.priority) "
				+ "from SimpleTaskDbEntity s  ";
		// Query query = createQuery(qString);
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

	private SimpleTaskDbEntity contains(List<SimpleTaskDbEntity> list, Long id) {
		for(SimpleTaskDbEntity t : list) {
			if (t.getId().equals(id)) {
				return t;
			}
		} 
		return null;
	}
	
	protected SimpleTaskSpringDataDao getDao() {
		return dao;
	}

	protected SimpleTaskDbEntity getDto() {
		return entity;
	}

	protected SimpleTaskDbEntity modifyDto(final SimpleTaskDbEntity dto) {
		dto.setTaskName("UpdatedName");
		// dto.getNotes().add("UpdatedNote");
		return dto;
	}

}
