package local.halflight.learning.model.amqp.converter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;

@Component
public class LearningMessageConverter implements MessageConverter {

	private static final Logger LOG = LoggerFactory.getLogger(LearningMessageConverter.class);

	XStream xStream;
	DefaultClassMapper defaultClassMapper;

	public LearningMessageConverter() {
		xStream = new XStream();
		
		defaultClassMapper = new DefaultClassMapper();
		
	}

	@Override
	public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		LOG.info("Serializing object {} to string", object);
		String sObject = xStream.toXML(object);
		LOG.info("Serialized object. string {}", sObject);
		xStream.toXML(object, outStream);
		
		String s = defaultClassMapper.getClassIdFieldName();
		LOG.info("defaultClassMapper. getClassIdFieldName {}", s);

		byte[] bytes = outStream.toByteArray();

		LOG.info("Convert bytes[] to string again {}", xStream.fromXML(new ByteArrayInputStream(bytes)));

		MessageProperties properties = messageProperties == null ? new MessageProperties() : messageProperties;
		properties.setHeader("mine-header", "Header for testing");
		
		properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
		properties.setContentLength(bytes.length);
		properties.setTimestamp(new Date());
		return new Message(bytes, properties);
	}

	@Override
	public Object fromMessage(Message message) throws MessageConversionException {
	
		LOG.info("Just recieved message {} ", message);
		byte[] messageBody = message.getBody();

		LOG.info("Extract message body (byte[]) {} ", messageBody);
		ByteArrayInputStream in = new ByteArrayInputStream(messageBody); 		
		
		Object someObject = xStream.fromXML(in);

		LOG.info("Deserialize to {} usiing xstream ", someObject);
		
		return someObject;
//		return messageConverter.fromMessage(message);
	}

}
