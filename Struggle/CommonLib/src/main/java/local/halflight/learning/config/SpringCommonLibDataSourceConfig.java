package local.halflight.learning.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@Import(value = { CommonLibHibernateConfig.class })
public class SpringCommonLibDataSourceConfig {
	private static final String JDBC_DRIVER_CLASS = "com.mysql.jdbc.Driver";

	@Value("${jdbc.url}")
	String jdbcUrl;
	@Value("${jdbc.username}")
	String jdbcUser;
	@Value("${jdbc.password}")
	String jdbcPassword;

	@Bean(name = "learningDataSource")
	DriverManagerDataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(JDBC_DRIVER_CLASS);
		dataSource.setPassword(jdbcPassword);
		dataSource.setUrl(jdbcUrl);
		dataSource.setUsername(jdbcUser);
		return dataSource;
	}
}
