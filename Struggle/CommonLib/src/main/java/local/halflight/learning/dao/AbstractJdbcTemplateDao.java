package local.halflight.learning.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

/***
 * General dao capabilities
 * @author halflight
 */
public abstract class AbstractJdbcTemplateDao<T> implements Dao<T> {

	protected JdbcTemplate template;

	protected Class<T> clazz;
	
	protected AbstractJdbcTemplateDao(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}
	
	
	
}
