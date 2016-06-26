package local.halflight.learning.model.handlers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import local.halflight.learning.dto.simpletask.SimpleTask;
import local.halflight.learning.dto.simpletask.SimpleTaskResponse;
import local.halflight.learning.model.handlers.tasks.TaskHandler;
import local.halflight.learning.model.handlers.tasks.TaskHandlerRegistry;

@Component
public class SimpleTaskRequestHandler implements RequestHandler<SimpleTaskResponse, SimpleTask> {
	
	private static final Logger LOG = LoggerFactory.getLogger(SimpleTaskRequestHandler.class);

	@Autowired
	private TaskHandlerRegistry taskHandlersRegistry;
	
	@Override
	public Optional<HandlerResponse<SimpleTaskResponse>> handle(SimpleTask payload) {
			LOG.info("SimpleTask handler handle()");

			TaskHandler handler = taskHandlersRegistry.getHandler(payload.getTaskType());
			SimpleTaskResponse taskResponse = (SimpleTaskResponse) handler.handle(payload);
			
			HandlerResponse reqestHandlerRp = new SimpleTaskHandlerResponse();
			reqestHandlerRp.setResponse(taskResponse);
			reqestHandlerRp.setStatus("SimpleTask handled succesfully");
			
			Optional<HandlerResponse<SimpleTaskResponse>> result = Optional.of(reqestHandlerRp); 
 			return result;
	}

	public Class<?> getSupportedType() {
		return SimpleTask.class;
	}
	
}
