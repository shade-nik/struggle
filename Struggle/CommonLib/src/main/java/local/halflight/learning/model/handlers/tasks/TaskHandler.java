package local.halflight.learning.model.handlers.tasks;

import java.util.Set;

public interface TaskHandler<A, R> {
	
	R handle(A a);
	
	Set<TaskType> supportedTypes();

}
