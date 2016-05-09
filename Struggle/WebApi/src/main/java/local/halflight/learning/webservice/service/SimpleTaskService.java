package local.halflight.learning.webservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import local.halflight.learning.dao.hibernate.simpletask.SimpleTaskHibernateDao;
import local.halflight.learning.dao.simpletask.SimpleTaskDao;
import local.halflight.learning.dto.simpletask.SimpleTask;
import local.halflight.learning.dto.simpletask.SimpleTaskDbEntity;
import local.halflight.learning.dto.simpletask.SimpleTaskEntityConverter;
import local.halflight.learning.testutils.TestDataSource;

@Service
public class SimpleTaskService {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleTaskService.class);

	SimpleTaskHibernateDao simpleTaskDao; 
	
	
	public SimpleTaskService() {
	}

	public List<String> getListOfStrings() {

		List<String> list = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			list.add("TaskIndex" + i);
		}

		return list;
	}

	public String findString(String taskId) {
		return "FoundedItem";
	}

	public List<SimpleTask> findAll() {
//		List<SimpleTask> taskList = TestDataSource.generateTaskList(4);
		List<SimpleTaskDbEntity> taskList = simpleTaskDao.retrieveAll();
		LOG.info("Found tasks: {}", taskList);
		//convert db entry to web dto old way
		//TODO use java 8
		List<SimpleTask> resp = new ArrayList<>();
		for(SimpleTaskDbEntity e : taskList)
		{
			resp.add(SimpleTaskEntityConverter.toDto(e));
		}
		return resp;
	}

	public SimpleTask findTask(String taskId) {

//		SimpleTask task = TestDataSource.generateTask();
		SimpleTaskDbEntity task = simpleTaskDao.findByNameWithNamedQuery(taskId);
		LOG.info("Found task: {}", task);

		return SimpleTaskEntityConverter.toDto(task);

	}
	
	public SimpleTask save(SimpleTask rq) {
		SimpleTaskDbEntity task = simpleTaskDao.save(SimpleTaskEntityConverter.toEntity(rq));
		LOG.info("Save task: {}", task);
		return SimpleTaskEntityConverter.toDto(task);
	}
	
	@Autowired
	public void setSimpleTaskDao(SimpleTaskHibernateDao simpleTaskDao) {
		this.simpleTaskDao = simpleTaskDao;
	}



}
