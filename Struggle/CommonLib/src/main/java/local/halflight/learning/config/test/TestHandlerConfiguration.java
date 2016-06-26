package local.halflight.learning.config.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(basePackages="local.halflight.learning.model.handlers")
@PropertySource({ "classpath:learning.properties" })
@Profile("test")
public class TestHandlerConfiguration {

	   @Bean
	   public static PropertySourcesPlaceholderConfigurer properties() {
	      return new PropertySourcesPlaceholderConfigurer();
	   }
}
