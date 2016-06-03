package local.halflight.learning.model.amqp.sender;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;


public interface Sender {	
	
	//Change for use some generic project message
	public void send(Message message);

	public void send(Message message, Queue queue);

	public void send(Message message, Exchange exchange, Queue queue);
	
	
	public default String getOutQueue() {
		return "WorkerQueue";
	}
	
}
