package local.halflight.learning.concurrent.model;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class TransactionManagerConfiguration {
	public static final String HIBERNATE_TRANSACTION_MANAGER = "hibernateTransactionManager";
	public static final String JDBC_TRANSACTION_MANAGER = "jdbcTransactionManager";

	@Bean(name = "hibernateTransactionManager")
	@Autowired
	public HibernateTransactionManager hibernateTransactionManager(SessionFactory sf) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sf);
		return txManager;
	}

	@Bean(name = "jdbcTransactionManager")
	@Autowired
	public PlatformTransactionManager jdbcTransactionManager(@Qualifier("learningDataSource") DataSource ds) {
		return new DataSourceTransactionManager(ds);
	}

}
