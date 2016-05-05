package local.halflight.learning.webservice.service;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleTaskServiceTest {
	

	private static final Logger LOG = LoggerFactory.getLogger(SimpleTaskService.class);
	private final IMocksControl control = EasyMock.createControl();
	private final SimpleTaskService simpleTaskService = control.createMock(SimpleTaskService.class);
	
	
	public SimpleTaskServiceTest() {
	}
		
	
	
	@Test
	public void shouldTest(){
		
	}

}
