package local.halflight.learning.model.handlers;

import java.util.Optional;

public interface RequestHandler<RP, P> {

	Optional<HandlerResponse<RP>> handle(P payload);
	
	Class<?> getSupportedType();
	

}
