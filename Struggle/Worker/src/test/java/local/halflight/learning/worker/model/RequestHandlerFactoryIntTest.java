package local.halflight.learning.worker.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import local.halflight.learning.dto.simpletask.SimpleTask;
import local.halflight.learning.dto.struggleuser.StruggleUser;
import local.halflight.learning.handlers.RequestHandler;
import local.halflight.learning.handlers.RequestHandlerFactory;
import local.halflight.learning.messages.validationinfo.ValidationInfoMessage;
import local.halflight.learning.testconfig.TestHandlerConfiguration;
import local.halflight.learning.worker.config.WorkerConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WorkerConfiguration.class, TestHandlerConfiguration.class })
@ActiveProfiles("test")
public class RequestHandlerFactoryIntTest {

	private static final Logger LOG = LoggerFactory.getLogger(RequestHandlerFactoryIntTest.class);
	private static final Class[] REQUEST_CLASSES = { SimpleTask.class, ValidationInfoMessage.class };

	@Autowired
	private RequestHandlerFactory requestHandlerFactory;

	@Test
	public void shouldReturnHandlerForSimpleTask() throws InstantiationException, IllegalAccessException {
		Optional<RequestHandler> handler = requestHandlerFactory.createHandler(new SimpleTask());
		assertThat(handler).as("Handler for simpleTask").isPresent();
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
