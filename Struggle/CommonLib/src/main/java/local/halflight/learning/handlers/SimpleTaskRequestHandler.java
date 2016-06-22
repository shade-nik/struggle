package local.halflight.learning.handlers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import local.halflight.learning.dto.simpletask.SimpleTask;
import local.halflight.learning.dto.simpletask.SimpleTaskResponse;

@Component
public class SimpleTaskRequestHandler implements RequestHandler<SimpleTaskResponse, SimpleTask> {
	
	private static final Logger LOG = LoggerFactory.getLogger(SimpleTaskRequestHandler.class);

	
	@Override
	public Optional<HandlerResponse<SimpleTaskResponse>> handle(SimpleTask payload) {
			LOG.info("SimpleTask handler handle()");

			HandlerResponse res = new SimpleTaskHandlerResponse();
			res.setStatus("SimpleTask handled succesfully");
			
			Optional<HandlerResponse<SimpleTaskResponse>> result = Optional.of(res); 
 			return result;
	}

	public Class<?> getSupportedType() {
		return SimpleTask.class;
	}
	
}
