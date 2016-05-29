package local.halflight.learning.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
	private static final Logger LOG = LoggerFactory.getLogger(RabbitConfig.class);

	@Value("${rabbit.host}")
	private String rabbitHost;
	@Value("${rabbit.port}")
	private String rabbitPort;
	@Value("${rabbit.username}")
	private String rabbitUsername;
	@Value("${rabbit.password}")
	private String rabbitPassword;
	@Value("${rabbit.queue}")
	private String rabbitQueueName;
	@Value("${rabbit.exchange}")
	private String rabbitExchange;

	@Bean
	public ConnectionFactory connectionFactory() {
		LOG.info("Creating RabbitMQ connection factory with address: {}", rabbitHost);
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setAddresses(rabbitHost);
		connectionFactory.setUsername(rabbitUsername);
		connectionFactory.setPassword(rabbitPassword);
		
		return connectionFactory;
	}
	
	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate rTemplate = new RabbitTemplate(connectionFactory());
		rTemplate.setRoutingKey(rabbitQueueName);
		rTemplate.setQueue(rabbitQueueName);
		return rTemplate;
	}
	
	@Bean
	public Queue queue()
	{
		return new Queue(rabbitQueueName);
	}
	
}
