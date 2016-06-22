package local.halflight.learning.handlers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import local.halflight.learning.dto.simpletask.SimpleTask;
import local.halflight.learning.handlers.registry.WorkerRequestHandlerRegistry;

@Component
public class RequestHandlerFactory {

	private static final Logger LOG = LoggerFactory.getLogger(RequestHandlerFactory.class);

	@Autowired
	private WorkerRequestHandlerRegistry handlerRegistry;
	
	@Autowired
	private SimpleTaskRequestHandler simpleTaskRequestHandler;
	
	public Optional<RequestHandler> createHandler(Object payload) {
		if(payload instanceof SimpleTask) {
			return Optional.of(simpleTaskRequestHandler);
		}
		return Optional.empty();
	}

	public Optional<RequestHandler> getHandlerFromRegistry(Object payload) {
		LOG.debug("Trying to find handler for object (class{}) {} ", payload.getClass(), payload);
		RequestHandler handler = handlerRegistry.getHandler(payload.getClass());
		LOG.debug("Found handler: {}", handler);
		return Optional.of(handler);
	}

}
