package local.halflight.learning.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class CommonLibHibernateConfig {

//	If the config is in a different @Configuration class, then you can @Autowired it:

	@Autowired
	@Qualifier("learningDataSource") 
	DataSource ds;
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		factoryBean.setDataSource(ds);
		factoryBean.setPackagesToScan(new String[] {"org.halflight.learning.hibernate.entities"});

		Properties props = new Properties();
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.format_sql", "true");
//		props.setProperty("hibernate.hbm2ddl.auto", "update");
		props.setProperty("hibernate.hbm2ddl.auto", "create");
		props.setProperty("hibernate.connection.CharSet", "utf8");
		props.setProperty("hibernate.connection.characterEncoding", "utf8");
		props.setProperty("hibernate.connection.useUnicode", "true");

//        <property name="hibernate.connection.CharSet">utf8</property>
//        <property name="hibernate.connection.characterEncoding">utf8</property>
//        <property name="hibernate.connection.useUnicode">true</property>
        
		factoryBean.setHibernateProperties(props);
		return factoryBean;
	}

	@Bean
	public BeanPostProcessor exceptionTranslator()
	{
		return new PersistenceExceptionTranslationPostProcessor();
	}
	@Bean
	@Autowired
	public HibernateTransactionManager hibernateTransactionManager(SessionFactory sf)
	{
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sf);
		return txManager;
	}
	

}
