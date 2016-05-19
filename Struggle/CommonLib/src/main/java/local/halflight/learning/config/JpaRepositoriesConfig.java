package local.halflight.learning.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(	basePackages = {JpaRepositoriesConfig.PACKAGE_TO_SCAN }, 	
					   	transactionManagerRef = TransactionManagerConfiguration.JPA_TRANSACTION_MANAGER)
public class JpaRepositoriesConfig {

	public static final String PACKAGE_TO_SCAN = "local.halflight.learning" ;
	
	@Bean
	public EntityManagerFactory entityManagerFactory(@Qualifier("learningDataSource") DataSource ds) {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(ds);
		factory.setJpaVendorAdapter(jpaVendorAdapter());
		factory.setPackagesToScan(PACKAGE_TO_SCAN);
	    factory.afterPropertiesSet();
	    return factory.getObject();
	}
	
	@Bean
	public HibernateJpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
		jpaAdapter.setShowSql(true);
		jpaAdapter.setGenerateDdl(false);
		jpaAdapter.setDatabase(Database.MYSQL);
		return jpaAdapter;
	}
}
