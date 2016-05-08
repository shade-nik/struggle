package local.halflight.learning.webservice.api.rest;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.expect;

import java.util.Arrays;

import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import local.halflight.learning.dto.simpletask.NonAnnotatedTaskListResponse;
import local.halflight.learning.dto.simpletask.SimpleTask;
import local.halflight.learning.testutils.TestDataSource;
import local.halflight.learning.webservice.service.SimpleTaskService;

public class SimpleRestApiTest {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleTaskRestApi.class);
	private static final String TASK_DESCRIPTION = "Task desctiption";
	private static final int TASK_NUMBER = 17;
	
	private static final String TASK_ID = "test-task-id";
	private static final String TEST_RESPONSE = "test-task-response";

	private SimpleTaskRestApi api;
	private SimpleTask payload;

	private final IMocksControl control = EasyMock.createControl();
	private final SimpleTaskService simpleTaskService = control.createMock(SimpleTaskService.class);
	
	@Before
	public void setupSimpleRestApi()
	{
		api = new SimpleTaskRestApi();
		api.setSimpleTaskService(simpleTaskService);
		payload = TestDataSource.generateTask();
	}
	

	@Test
	public void shouldGetTask() {

		expect(simpleTaskService.findTask(anyString())).andReturn(payload);
		control.replay();
		Response rp = api.getTask(TASK_ID);
		control.verify();
		LOG.info("shouldGetTask Rp: {}", rp);

	}
	
	@Test
	public void shouldReturnIntegerForTaskIdInteger() {

		final String taskId = "Integer";
		
		control.replay();
		Response rp = api.getTask(taskId);
		control.verify();
		LOG.info("shouldReturnIntegerForTaskIdInteger Rp: {}", rp);
	}
	
	@Test
	public void shouldGetTaskList() {

		expect(simpleTaskService.findAll()).andReturn(Arrays.asList(payload));
		
		control.replay();
		JAXBElement<NonAnnotatedTaskListResponse> jaxbRp = api.getTasks(10);
		NonAnnotatedTaskListResponse rp = jaxbRp.getValue();
		control.verify();
		LOG.info("shouldGetTaskList Rp: {}", rp);
	}
	


}
