package local.halflight.learning.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Component
public class Worker {
	
	private static final String APP_CONTEXT = "worker-context.xml";
	
	private static final Logger LOG = LoggerFactory.getLogger(Worker.class);
	
	@Value("${rabbit.queue}")
	private static String queueName;
	
	@Autowired
	private ConnectionFactory connectionFactory;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(APP_CONTEXT);
		
		ctx.afterPropertiesSet();

		//Listener container required for async message consumption
		final SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
		final MessageConverter messageConverter = new SimpleMessageConverter();
		
		messageListenerContainer.setConnectionFactory(connectionFactory);
		messageListenerContainer.setQueueNames(queueName);
		
		//callback for message handling
		messageListenerContainer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				final Task task = (Task) messageConverter.fromMessage(message);
			}
		});
		//Error handler
		messageListenerContainer.setErrorHandler(new ErrorHandler() {
			
			@Override
			public void handleError(Throwable e) {
				LOG.info("Error while listening for messages", e);
			}
		});
		
		//shutdown hook to ensure container shutdown
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				LOG.info("Shutting down worker");
				messageListenerContainer.shutdown();
			}
		});

		
		messageListenerContainer.start();
	}

}
