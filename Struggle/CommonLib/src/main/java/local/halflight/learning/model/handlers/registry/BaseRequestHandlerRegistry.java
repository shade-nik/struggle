package local.halflight.learning.model.handlers.registry;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import local.halflight.learning.model.handlers.RequestHandler;

//@Component
@SuppressWarnings("rawtypes")
public class BaseRequestHandlerRegistry {

	private Map<Class<?>, RequestHandler> registry;
	
//	@Autowired
	public BaseRequestHandlerRegistry(Collection<RequestHandler> handlers) {
		registry = new HashMap<>();
		register(handlers);
	}
	
	public RequestHandler getHandler(Class<?> type) {
		RequestHandler handler = registry.get(type);
		com.google.common.base.Preconditions.checkNotNull(handler, "Can't find handler for this type : " + type);
		return handler;
	}

	private void register(Collection<RequestHandler> handlers) {
		for(RequestHandler handler : handlers) {
			Class<?> supportedType = handler.getSupportedType();
			registry.put(supportedType, handler);
		}
	}

}
