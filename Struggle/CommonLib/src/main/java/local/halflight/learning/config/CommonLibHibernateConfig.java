package local.halflight.learning.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

@Configuration
public class CommonLibHibernateConfig {

//	If the config is in a different @Configuration class, then you can @Autowired it:

	@Autowired
	@Qualifier("learningDataSource") 
	DataSource ds;
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		factoryBean.setDataSource(ds);
		factoryBean.setPackagesToScan(new String[] {"local.halflight.learning.dto.hibernate"});

		Properties props = new Properties();
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.format_sql", "true");
		props.setProperty(AvailableSettings.HBM2DDL_AUTO, "create");
		//		props.setProperty("hibernate.hbm2ddl.auto", "create");
		props.setProperty("hibernate.connection.CharSet", "utf8");
		props.setProperty("hibernate.connection.characterEncoding", "utf8");
		props.setProperty("hibernate.connection.useUnicode", "true");
        
		factoryBean.setHibernateProperties(props);
		return factoryBean;
	}

	@Bean
	public BeanPostProcessor exceptionTranslator()
	{
		return new PersistenceExceptionTranslationPostProcessor();
	}
	


}
