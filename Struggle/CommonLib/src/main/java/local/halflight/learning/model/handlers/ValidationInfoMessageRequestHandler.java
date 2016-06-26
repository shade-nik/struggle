package local.halflight.learning.model.handlers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import local.halflight.learning.dto.elasticsearch.ValidationInfoMessage;

@Component
public class ValidationInfoMessageRequestHandler implements RequestHandler<Void, ValidationInfoMessage> {

	private static final Logger LOG = LoggerFactory.getLogger(ValidationInfoMessageRequestHandler.class) ;
	

	
	
	@Override
	public Optional<HandlerResponse<Void>> handle(ValidationInfoMessage payload) {
		
		return Optional.empty();
	}

	@Override
	public Class<?> getSupportedType() {
		return ValidationInfoMessage.class;
	}

}
