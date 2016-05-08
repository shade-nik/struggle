package local.halflight.learning.dao.simpletask;

import java.util.List;

import javax.sql.DataSource;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import local.halflight.learning.dao.AbstractDao;
import local.halflight.learning.dto.simpletask.SimpleTask;

public class SimpleTaskDao extends AbstractDao<SimpleTask> {

	@Autowired
	public SimpleTaskDao(@Qualifier("learningDataSource") DataSource learningDatasouce) {
		super(learningDatasouce);
	}

	@Override
	public SimpleTask findById(Integer id) {
		throw new NotYetImplementedException();

	}

	@Override
	public SimpleTask findByStringId(String id) {
		throw new NotYetImplementedException();

	}

	@Override
	public SimpleTask update(SimpleTask dto) {
		throw new NotYetImplementedException();

	}

	@Override
	public void delete(SimpleTask dto) {
		throw new NotYetImplementedException();

	}

	@Override
	public List<SimpleTask> retrieveAll() {
		throw new NotYetImplementedException();

	}

	@Override
	public List<SimpleTask> queryForList(String query, Object[] params) {
		throw new NotYetImplementedException();

	}

}
