package local.halflight.learning.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.MessagingMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import local.halflight.learning.model.amqp.converter.LearningMessageConverter;

@Configuration
@EnableRabbit
public class RabbitConfig {
	private static final Logger LOG = LoggerFactory.getLogger(RabbitConfig.class);
	private static final boolean DURABLE_EXCHAGE = true;
	private static final boolean AUTOREMOVE_EXCHAGE = false;
	
	
	
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
	@Value("${rabbit.direct.exchange}")
	private String rabbitDirectExchange;
	@Value("${rabbit.topic.exchange}")
	private String rabbitTopicExchange;

	@Bean(name = "learningConnectionFactory")
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
		rTemplate.setMessageConverter(messageConverter());
		rTemplate.setExchange(rabbitDirectExchange);
		return rTemplate;
	}
	
	@Bean(name = "stubConverter")
	MessageConverter messageConverter() {
		MessageConverter converter = new LearningMessageConverter();
		
		
		return converter;
	}

	@Bean
	public Queue queue() {
		//durable, exclusive, autoDelete
		return new Queue(rabbitQueueName, true, false, false);
	}

	@Bean
	public RabbitAdmin rabbitAdmin() {
		RabbitAdmin adm = new RabbitAdmin(connectionFactory());
		return adm;
	}

	//?
	@Bean 
	Binding defaultBinding() {
		return BindingBuilder.bind(queue()).to(directExchange()).withQueueName();
	}
	
	@Bean
	public DirectExchange directExchange() {
		return new DirectExchange(rabbitDirectExchange, DURABLE_EXCHAGE, AUTOREMOVE_EXCHAGE);
	}
	

	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange(rabbitTopicExchange, DURABLE_EXCHAGE, AUTOREMOVE_EXCHAGE);
	}
	
	
}


