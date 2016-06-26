package local.halflight.learning.dao.springdatajpa.user;

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

import local.halflight.learning.dao.hibernate.simpletask.SimpleTaskHibernateDao;
import local.halflight.learning.entity.simpletask.SimpleTaskDbEntity;
import local.halflight.learning.entity.struggleuser.ProfileEntity;
import local.halflight.learning.entity.struggleuser.UserEntity;
import local.halflight.learning.testutils.TestDataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:common-test-beans.xml" })
@Rollback
public class StruggleUserHibernateDaoIntTest {
	// TODO extract abstract super

	private static final Logger LOG = LoggerFactory.getLogger(StruggleUserHibernateDaoIntTest.class);

	private static final String TEST_USERNAME = "TestUser";

	@Autowired
	private UserEntitySpringDataDao userEntitySpringDataDao;

	UserEntity entity;
	
	@Before
	public void before() {
		entity = TestDataSource.User.generateUser(TEST_USERNAME);
		 UserEntity cleanup =
				 userEntitySpringDataDao.findByName(entity.getUsername());
		 if (cleanup != null) {
			 userEntitySpringDataDao.delete(cleanup.getId());
		 }
	}

	@After
	public void after() {
	}

	@Test
	@Rollback
	public void testCrud() throws Exception {
		UserEntitySpringDataDao dao = getDao();
		UserEntity dto = getDto();
		dto.setId(null);

		LOG.info("===Generated UserEntity: {}", dto);
		// save
		UserEntity saved = dao.save(dto);
		assertTrue("Saved dto should be not null", saved != null);
		assertTrue("Saved dto id should be not null", saved.getId() != null);
		LOG.info("===Save it: {}", saved);

		// read
		UserEntity founded = dao.findOne(saved.getId());
		assertThat(founded).isNotNull();
		assertThat(founded).isEqualToComparingOnlyGivenFields(saved, "id");

		// update
		modifyDto(founded);
		UserEntity updated = dao.save(founded);
		assertThat(updated).isNotNull();
		LOG.info("===Update it: {}", updated);

		// remove
		dao.delete(updated.getId());
	
		UserEntity notFound = dao.findOne(updated.getId());
		assertThat(notFound).isNull();

	}


	@Test
	@Rollback
	public void testFindByGeneratedUUid() {
		
		UserEntitySpringDataDao dao = getDao();
		UserEntity dto = getDto();
		dto.setId(null);
		
		// save
		UserEntity saved = dao.save(dto);
		assertTrue("Saved dto should be not null", saved != null);
		assertTrue("uuid must be generated and shouldn't be null", saved.getUserUUID() != null);

		LOG.info("===Saved dto: {}", saved);
		LOG.info("===UUID: {}", saved.getUserUUID());
		//findByUUID
		UserEntity userByUUID = dao.findByUUID(saved.getUserUUID());
		assertTrue("findById res should be not null", userByUUID != null);
	}

	
	@Test
	@Rollback
	public void testProfileFetching() {
		
		UserEntitySpringDataDao dao = getDao();
		UserEntity dto = getDto();
		dto.setId(null);
		
		// save
		UserEntity saved = dao.save(dto);
		assertTrue("Saved dto should be not null", saved != null);
		assertTrue("uuid must be generated and shouldn't be null", saved.getUserUUID() != null);

		//findByUUID
		ProfileEntity profile = saved.getProfile();
		LOG.info("===Fetch profile: {}", profile);
	}
	
	protected UserEntitySpringDataDao getDao() {
		return userEntitySpringDataDao;
	}

	protected UserEntity getDto() {
		return entity;
	}

	protected UserEntity modifyDto(final UserEntity dto) {
		dto.setUsername("UpdatedName");
		return dto;
	}
	


}
