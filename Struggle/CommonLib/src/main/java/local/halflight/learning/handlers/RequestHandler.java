package local.halflight.learning.handlers;

import java.util.Optional;

public interface RequestHandler {

	Optional<BaseLearningMessage> handle(Object payload);

}
