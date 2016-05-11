package local.halflight.learning.webservice.service;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import local.halflight.learning.dao.hibernate.simpletask.SimpleTaskHibernateDao;

public class SimpleTaskServiceTest {
	

	private static final Logger LOG = LoggerFactory.getLogger(SimpleTaskService.class);
	private final IMocksControl control = EasyMock.createControl();

	private final SimpleTaskService service = new SimpleTaskService();
	private final SimpleTaskHibernateDao simpleTaskHibernateDao = control.createMock(SimpleTaskHibernateDao.class);

	
	public SimpleTaskServiceTest() {
		
	}

	@Before
	public void setup()
	{
		service.setSimpleTaskHibernateDao(simpleTaskHibernateDao);
	}
	
	
	@Test
	public void shouldTest(){
		
	}

}
