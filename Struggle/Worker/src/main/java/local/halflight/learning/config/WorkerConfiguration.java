package local.halflight.learning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import local.halflight.learning.model.Worker;

@Configuration
public class WorkerConfiguration {

	
	@Bean
	Worker mainWorker() {
		return new Worker();
	} 
}
