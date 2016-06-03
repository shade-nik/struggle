package local.halflight.learning.model.amqp.sender;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class BaseSender extends AbstractMessageSender {

	@Autowired
	public BaseSender(RabbitTemplate rabbitTemplate) {
		super(rabbitTemplate);
	}
	
	@Override
	public void send(Message message, Queue queue) {
		getRabbitTemplate().send(queue.getName(), message);
	}
	
	@Override
	public void send(Message message, Exchange exchange, Queue queue) {
	
		getRabbitTemplate().send(exchange.getName(), queue.getName(), message);
	}

	@Override
	public void send(Message message) {
		super.send(message);
	}

	public void sendObjToDefault(Object msg) {
		getRabbitTemplate().convertAndSend(msg);
	}
	
	public void sendObj(Object msg, String queue) {
		getRabbitTemplate().convertAndSend(queue, msg);
	}
	
	public void sendObj(Object msg,  String exchange, String queue) {
		getRabbitTemplate().convertAndSend(exchange, queue, msg);
	}
	
	
}

