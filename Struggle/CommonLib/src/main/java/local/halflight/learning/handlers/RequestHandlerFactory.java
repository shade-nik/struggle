package local.halflight.learning.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import local.halflight.learning.dto.simpletask.SimpleTask;

@Component
public class RequestHandlerFactory {

	private static final Logger LOG = LoggerFactory.getLogger(RequestHandlerFactory.class);

	
	@Autowired
	SimpleTaskRequestHandler simpleTaskRequestHandler;
	
	public RequestHandler createHandler(Object payload) {
		if(payload instanceof SimpleTask) {
			return simpleTaskRequestHandler;
		}
		return null;
	}

}
