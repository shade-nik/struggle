package local.halflight.learning.worker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import local.halflight.learning.config.RabbitConfig;

@Configuration
@ComponentScan
@PropertySource("classpath:learning.properties")
@Import(RabbitConfig.class)
public class WorkerConfiguration {	
	
	/**
	 * In order to resolve ${...} placeholders in definitions or 
	 * @Value annotations using properties from a PropertySource, 
	 * one must register a PropertySourcesPlaceholderConfigurer. 
	 * This happens automatically when using in XML, but must be
	 *  explicitly registered using a static @Bean method when 
	 *  using @Configuration classes.
	 */
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
     }
	
}
