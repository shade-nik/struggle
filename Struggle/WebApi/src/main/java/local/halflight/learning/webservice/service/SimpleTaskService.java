package local.halflight.learning.webservice.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import local.halflight.learning.dao.hibernate.simpletask.SimpleTaskHibernateDao;
import local.halflight.learning.dao.springdatajpa.SimpleTaskSpringDataDao;
import local.halflight.learning.dto.hibernate.simpletask.SimpleTaskDbEntity;
import local.halflight.learning.dto.simpletask.SimpleTask;
import local.halflight.learning.dto.simpletask.SimpleTaskEntityConverter;
import local.halflight.learning.model.TaskType;
import local.halflight.learning.model.sender.SimpleTaskSender;

@Service
public class SimpleTaskService {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleTaskService.class);

	private SimpleTaskHibernateDao simpleTaskHibernateDao;
	
	@Autowired
	@Qualifier("simpleTaskSpringDataDao")
	private SimpleTaskSpringDataDao springDataDao;
	
	//TODO think about struct (send entity for processing)
	// local executor, amqp, persist... etc
	SimpleTaskSender simpleTaskSender; 

	public SimpleTaskService() {
	}

	public List<SimpleTask> findAll() {
		List<SimpleTaskDbEntity> taskList = simpleTaskHibernateDao.retrieveAll();
		LOG.info("Found tasks: {}", taskList);

		List<SimpleTask> resp =  SimpleTaskEntityConverter.convertList(taskList, (e)-> SimpleTaskEntityConverter.toDto(e) );
		return resp;
	}

	public SimpleTask jpaDaoFindTask(String taskId) {
		return SimpleTaskEntityConverter.toDto(springDataDao.findOne(Long.valueOf(taskId)));
	}
	
	public SimpleTask findTask(String taskId) {

		SimpleTaskDbEntity task = null;
		if (StringUtils.isNumeric(taskId)) {
			task = simpleTaskHibernateDao.findById(Long.valueOf(taskId));
		} else {
			String taskName = taskId;
			try {
				task = simpleTaskHibernateDao.findByNameWithNamedQuery(taskName);

			} catch (Exception e) {
				LOG.info("Exception while searching: {}", e);
			}
		}
		LOG.info("Found task: {}", task);
		return SimpleTaskEntityConverter.toDto(task);

	}

	public SimpleTask save(SimpleTask rq) {
		
		
		if(rq != null && rq.getTaskType() != null) {
			if (TaskType.ASYNC.equals(rq.getTaskType())) { 
				
			}
		}
		
		try {
			SimpleTaskDbEntity task = simpleTaskHibernateDao.save(SimpleTaskEntityConverter.toEntity(rq));
			LOG.info("Saved task: {}", task);
			return SimpleTaskEntityConverter.toDto(task);
		} catch (Exception e ) {
			LOG.info("Save failed: ", e);
			return null;
		}
	}

	public SimpleTask update(SimpleTask update) {
		try {
			SimpleTaskDbEntity task = simpleTaskHibernateDao.update(SimpleTaskEntityConverter.toEntity(update));
			LOG.info("Updated task: {}", task);
			return SimpleTaskEntityConverter.toDto(task);
		} catch (Exception e ) {
			LOG.info("Update failed: ", e);
			return null;
		}
	}

	public void remove(String taskId) {
		LOG.info("Remove task:{} requested", taskId);
		Long id = Long.getLong(taskId);
		simpleTaskHibernateDao.delete(id);
	}
	
	@Autowired
	public void setSimpleTaskHibernateDao(SimpleTaskHibernateDao simpleTaskHibernateDao) {
		this.simpleTaskHibernateDao = simpleTaskHibernateDao;
	}

}
