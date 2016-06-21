package local.halflight.learning.worker.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import local.halflight.learning.worker.config.WorkerConfiguration;

public class Worker {
	
	private static final String APP_CONTEXT = "worker-spring-ctx.xml";
	private static final Logger LOG = LoggerFactory.getLogger(WorkerProcessor.class);

	
	public static void main(String[] args) {
		LOG.info("Loading application context for {}", APP_CONTEXT);
		final ApplicationContext ctx = new ClassPathXmlApplicationContext(APP_CONTEXT);
//		final ApplicationContext ctx = new AnnotationConfigApplicationContext(WorkerConfiguration.class);
		LOG.info("Application context loaded {}", ctx);
		LOG.info("Getting processor bean");
		WorkerProcessor processor = ctx.getBean(WorkerProcessor.class);
		LOG.info("Bean found: {}", processor);
		processor.start();
		LOG.info(" processor.start() called");

	}	
		
}
