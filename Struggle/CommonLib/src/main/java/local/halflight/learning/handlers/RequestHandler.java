package local.halflight.learning.handlers;

import java.util.Optional;

import local.halflight.learning.dto.Payload;

public interface RequestHandler<RP, P extends Payload> {

	Optional<HandlerResponse<RP>> handle(P payload);
	
	Class<?> getSupportedType();
	

}
