package local.halflight.learning.testconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ComponentScan(basePackages="local.halflight.learning.handlers")
@Profile("test")
public class TestHandlerConfiguration {
	
}
