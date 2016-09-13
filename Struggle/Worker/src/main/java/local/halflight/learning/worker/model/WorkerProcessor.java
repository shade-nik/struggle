package local.halflight.learning.worker.model;

import java.util.NoSuchElementException;
import java.util.Optional;

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

import local.halflight.learning.dto.GenericResponse;
import local.halflight.learning.dto.Payload;
import local.halflight.learning.model.handlers.HandlerResponse;
import local.halflight.learning.model.handlers.RequestHandler;
import local.halflight.learning.worker.model.handler.WorkerHandlerFactory;

@Component
public class WorkerProcessor {
	
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
	private WorkerHandlerFactory requestHandlerFactory;
	
	public void start() {
		
		messageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);		
		messageListenerContainer.setQueueNames(queueName);
		messageListenerContainer.setMessageConverter(messageConverter);
		//callback for message handling
		//move to super or elsewhere
		messageListenerContainer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				Object recieved = messageConverter.fromMessage(message);
				Payload recieviedPayload = null;
				if(recieved instanceof Payload) {
					recieviedPayload = (Payload) recieved;
					try {
						RequestHandler rqh = requestHandlerFactory.getHandlerFromRegistry(recieviedPayload).orElseThrow(NoSuchElementException::new);
						LOG.info("==Handler for message created");
						LOG.info("==Handling message... invoke sleep to");
						Optional<HandlerResponse<GenericResponse>> result =  rqh.handle(recieviedPayload);
						LOG.info("Message handled. result {} ", result);
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						LOG.info("==Caught InterruptedException", e);
					} catch (NoSuchElementException e) {
						LOG.error("Can't find handler for message {}", recieved );
					}
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
