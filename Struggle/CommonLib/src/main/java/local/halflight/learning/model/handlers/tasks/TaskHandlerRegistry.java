package local.halflight.learning.model.handlers.tasks;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskHandlerRegistry {

	Map<TaskType, TaskHandler> taskHandlersRegistry;
	
	@Autowired
	public TaskHandlerRegistry(Collection<TaskHandler> taskHandlers) {
		taskHandlersRegistry = new HashMap<>();
		register(taskHandlers);
	}
	
	@SuppressWarnings("rawtypes")
	public TaskHandler getHandler(TaskType taskType) {
		return taskHandlersRegistry.get(taskType);
	} 

	@SuppressWarnings({ "unused", "rawtypes" })
	private void register(Collection<TaskHandler> taskProcessors) {
		for(TaskHandler task : taskProcessors) {
			Set<TaskType> supportedTypes = task.supportedTypes(); 
			for (TaskType type : supportedTypes) {
				this.taskHandlersRegistry.put(type, task);
			}
		}
	}

}
