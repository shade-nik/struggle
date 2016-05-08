package local.halflight.learning.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

/***
 * General dao capabilities
 * @author halflight
 */
public abstract class AbstractDao<T> implements Dao<T> {

	protected JdbcTemplate template;

	protected Class<T> clazz;
	
	protected AbstractDao(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}
	
	
	
}
