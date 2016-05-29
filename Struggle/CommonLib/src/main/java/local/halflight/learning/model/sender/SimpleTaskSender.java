package local.halflight.learning.model.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import local.halflight.learning.dto.simpletask.SimpleTask;
import local.halflight.learning.model.TaskResponse;

@Component
public class SimpleTaskSender implements Sender<SimpleTask> {

	@Autowired
	private AmqpTemplate amqpTemplate; 
	
	@Override
	public TaskResponse send(SimpleTask task) {
		amqpTemplate.convertAndSend(task);
		
		return null;
	}
}

