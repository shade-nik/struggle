package local.halflight.learning.model.handlers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import local.halflight.learning.dto.simpletask.SimpleTask;
import local.halflight.learning.dto.simpletask.SimpleTaskRequest;
import local.halflight.learning.model.handlers.registry.BaseRequestHandlerRegistry;

//@Component
public class BaseRequestHandlerFactory {

	private static final Logger LOG = LoggerFactory.getLogger(BaseRequestHandlerFactory.class);

	protected BaseRequestHandlerRegistry handlerRegistry;
	
	public Optional<RequestHandler> getHandlerFromRegistry(Object payload) {
		LOG.debug("Trying to find handler for object (class{}) {} ", payload.getClass(), payload);
		RequestHandler handler = handlerRegistry.getHandler(payload.getClass());
		LOG.debug("Found handler: {}", handler);
		return Optional.of(handler);
	}

	public void setHandlerRegistry(BaseRequestHandlerRegistry handlerRegistry) {
		this.handlerRegistry = handlerRegistry;
	}
}
