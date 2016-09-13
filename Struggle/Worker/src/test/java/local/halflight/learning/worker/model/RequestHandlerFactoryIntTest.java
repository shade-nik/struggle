package local.halflight.learning.worker.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import local.halflight.learning.config.test.TestHandlerConfiguration;
import local.halflight.learning.dto.simpletask.SimpleTask;
import local.halflight.learning.dto.simpletask.SimpleTaskRequest;
import local.halflight.learning.model.handlers.RequestHandler;
import local.halflight.learning.model.handlers.BaseRequestHandlerFactory;
import local.halflight.learning.testutils.TestDataSource;
import local.halflight.learning.worker.config.WorkerConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WorkerConfiguration.class, TestHandlerConfiguration.class })
@ActiveProfiles("test")
@Ignore
public class RequestHandlerFactoryIntTest {

	private static final Logger LOG = LoggerFactory.getLogger(RequestHandlerFactoryIntTest.class);
	private static final Class[] REQUEST_CLASSES = { SimpleTask.class };

	@Autowired
	private BaseRequestHandlerFactory requestHandlerFactory;

	private SimpleTaskRequest request;
	
	
	@Before
	public void before() {
		request = new SimpleTaskRequest();
		request.setPayload(TestDataSource.generateTask());
	}
	
	@Ignore @Test
	public void shouldReturnHandlerForSimpleTaskRequest() throws InstantiationException, IllegalAccessException {
		Optional<RequestHandler> handler = requestHandlerFactory.getHandlerFromRegistry(new SimpleTask());
		assertThat(handler).as("Handler for simpleTask").isPresent();
	}

	@Test
	public void shouldHandleSimpleTaskReq() throws InstantiationException, IllegalAccessException {
		Optional<RequestHandler> handlerOpt = requestHandlerFactory.getHandlerFromRegistry(new SimpleTask());
		
		assertThat(handlerOpt).as("Handler for simpleTask").isPresent();
		RequestHandler handler = handlerOpt.get();
		
		Object response = handler.handle(new SimpleTask());
	}


	@Test
	public void shouldReturnHandlerFor() throws InstantiationException, IllegalAccessException {
		for (Class clazz : REQUEST_CLASSES) {
			Object rq = clazz.newInstance();
			getHandlerFromFactory(rq);
		}
	}

	private void getHandlerFromFactory(Object rq) {
		Optional<RequestHandler> handler = requestHandlerFactory.getHandlerFromRegistry(rq);
		assertThat(handler).as("Handler for request {}", rq).isPresent();
	}

}
