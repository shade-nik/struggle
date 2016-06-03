package local.halflight.learning.handlers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;

//implement processor, listening container...
public abstract class AbstractWorkerProcessor  {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractWorkerProcessor.class);
	
	private RequestHandlerFactory requestHandlerFactory; 
	
	
	//TODO add own data types
	BaseLearningMessage processMessage(LearningMessage message) {
		
		LearningPayload payload = message.getPayload();
			
		RequestHandler handler = requestHandlerFactory.createHandler(payload); 
		
		Optional<BaseLearningMessage> response = handler.handle(payload);
		
		return response.get();
	}
	
	
}
