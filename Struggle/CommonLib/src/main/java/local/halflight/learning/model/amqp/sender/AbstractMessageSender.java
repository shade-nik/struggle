package local.halflight.learning.model.amqp.sender;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitGatewaySupport;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public abstract class AbstractMessageSender extends RabbitGatewaySupport implements Sender {

	public AbstractMessageSender(RabbitTemplate rabbitTemplate) {
		super();
		setRabbitTemplate(rabbitTemplate);
		
	}

	
	@Override
	public void send(Message message) {
		Message amqpMessage = getRabbitTemplate().getMessageConverter().toMessage(message, null);
		
		getRabbitTemplate().send(getOutQueue(), amqpMessage);
	}



	
	
}
