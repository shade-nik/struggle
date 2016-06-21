package local.halflight.learning.worker.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

import local.halflight.learning.handlers.AbstractWorkerProcessor;
import local.halflight.learning.handlers.BaseLearningMessage;
import local.halflight.learning.handlers.RequestHandler;
import local.halflight.learning.handlers.RequestHandlerFactory;

@Component
public class WorkerProcessor extends AbstractWorkerProcessor {
	
	private static final Logger LOG = LoggerFactory.getLogger(WorkerProcessor.class);
	
	@Value("${rabbit.queue}")
	private  String queueName;
	
	@Autowired
	@Qualifier("learningConnectionFactory")
	private ConnectionFactory connectionFactory;
	
	private SimpleMessageListenerContainer messageListenerContainer;

	@Autowired
	@Qualifier("stubConverter")
	private MessageConverter messageConverter;

	@Autowired
	private RequestHandlerFactory requestHandlerFactory;
	
	public void start() {
		
		messageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);		
		messageListenerContainer.setQueueNames(queueName);
		messageListenerContainer.setMessageConverter(messageConverter);
		//callback for message handling
		messageListenerContainer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				Object recieved = messageConverter.fromMessage(message);
				
				RequestHandler rqh = requestHandlerFactory.createHandler(recieved);
				LOG.info("==Handler for message created");
				try {
					LOG.info("==Handling message... invoke sleep to");
					BaseLearningMessage result =  rqh.handle(recieved).get();
					LOG.info("Message handled. result {} ", result.getStatusString());
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					LOG.info("==Caught InterruptedException", e);
				}
				
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

		LOG.info("Starting message listener container");
		messageListenerContainer.start();
		LOG.info("Started message listener container");
	}

}
