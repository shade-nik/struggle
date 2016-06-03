package local.halflight.learning.handlers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import local.halflight.learning.dto.simpletask.SimpleTask;

@Component
public class SimpleTaskRequestHandler implements RequestHandler {
	private static final Logger LOG = LoggerFactory.getLogger(SimpleTaskRequestHandler.class);

	@Override
	public Optional<BaseLearningMessage> handle(Object payload) {
		if(payload instanceof SimpleTask) 
		{
			LOG.info("SimpleTask handler handle()");
			BaseLearningMessage res = new BaseLearningMessage();
			res.setStatusString("SimpleTask handled succesfully");
			
			Optional<BaseLearningMessage> result = Optional.of(res); 
 			return result;
		}
		
		return null;
	}

}
