package local.halflight.learning.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class TransactionManagerConfiguration {
	private static final Logger LOG = LoggerFactory.getLogger(TransactionManagerConfiguration.class);

	public static final String HIBERNATE_TRANSACTION_MANAGER = "hibernateTransactionManager";
	public static final String JDBC_TRANSACTION_MANAGER = "jdbcTransactionManager";
	public static final String JPA_TRANSACTION_MANAGER = "jpaTransactionManager";

	@Bean(name = "hibernateTransactionManager")
	@Autowired
	public HibernateTransactionManager hibernateTransactionManager(SessionFactory sf) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sf);
		LOG.info("hibernateTransactionManager created");
		return txManager;
	}

	@Bean(name = "jpaTransactionManager")
	@Primary
	@Autowired
	public PlatformTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);
		LOG.info("jpaTransactionManager created");
		return txManager;
	}

	@Bean(name = "jdbcTransactionManager")
	@Autowired
	public PlatformTransactionManager jdbcTransactionManager(@Qualifier("learningDataSource") DataSource ds) {
		LOG.info("jdbcTransactionManager created");
		return new DataSourceTransactionManager(ds);
	}

}
