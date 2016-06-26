package local.halflight.learning.model.handlers.tasks;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import local.halflight.learning.dto.simpletask.SimpleTask;
import local.halflight.learning.dto.simpletask.SimpleTaskResponse;


@Component
public class SimpleTaskElasticRequestHandler implements TaskHandler<SimpleTask, SimpleTaskResponse>{

	
	@Override
	public SimpleTaskResponse handle(SimpleTask a) {
		SimpleTaskResponse rp = new SimpleTaskResponse();
		
		
		return rp;
	}

	@Override
	public Set<TaskType> supportedTypes() {
		return new HashSet<>(Arrays.asList(TaskType.ELASTIC));
	}

}
