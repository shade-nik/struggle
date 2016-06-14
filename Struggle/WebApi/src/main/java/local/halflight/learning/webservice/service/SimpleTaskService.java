package local.halflight.learning.webservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import local.halflight.learning.config.TransactionManagerConfiguration;
import local.halflight.learning.dao.hibernate.simpletask.SimpleTaskHibernateDao;
import local.halflight.learning.dao.springdatajpa.SimpleTaskSpringDataDao;
import local.halflight.learning.dto.hibernate.simpletask.SimpleTaskDbEntity;
import local.halflight.learning.dto.simpletask.SimpleTask;
import local.halflight.learning.dto.simpletask.SimpleTaskEntityConverter;
import local.halflight.learning.model.TaskType;
import local.halflight.learning.model.amqp.sender.BaseSender;

@Service
public class SimpleTaskService {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleTaskService.class);

	private SimpleTaskHibernateDao simpleTaskHibernateDao;
	
	@Autowired
	@Qualifier("simpleTaskSpringDataDao")
	private SimpleTaskSpringDataDao springDataDao;
	
	//TODO think about struct (send entity for processing)
	// local executor, amqp, persist... etc
	private BaseSender baseSender;
	
	public SimpleTaskService() {
	}

	@Transactional(value = TransactionManagerConfiguration.HIBERNATE_TRANSACTION_MANAGER)
	public List<SimpleTask> retrieveAll() {
		List<SimpleTaskDbEntity> taskList = simpleTaskHibernateDao.retrieveAll();
		LOG.info("Found tasks: {}", taskList);

		List<SimpleTask> resp = taskList.stream().map(SimpleTaskEntityConverter::toDto).collect(Collectors.toList());
//		List<SimpleTask> resp =  SimpleTaskEntityConverter.convertList(taskList, (e)-> SimpleTaskEntityConverter.toDto(e) );
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

	public Optional<SimpleTask> save(SimpleTask task) {
		
		
		if(task != null && task.getTaskType() != null) {
			if (TaskType.ASYNC.equals(task.getTaskType())) { 
				baseSender.sendObjToDefault(task);
			}
		}
		
		try {
			SimpleTaskDbEntity entity = simpleTaskHibernateDao.save(SimpleTaskEntityConverter.toEntity(task));
			LOG.info("Saved task: {}", entity);
			return Optional.of(SimpleTaskEntityConverter.toDto(entity));
		} catch (Exception e ) {
			LOG.info("Save failed: ", e);
			return Optional.empty();
		}
	}

	public Optional<SimpleTask> update(SimpleTask update) {
		try {
			SimpleTaskDbEntity task = simpleTaskHibernateDao.update(SimpleTaskEntityConverter.toEntity(update));
			LOG.info("Updated task: {}", task);
			return Optional.of(SimpleTaskEntityConverter.toDto(task));
		} catch (Exception e ) {
			LOG.info("Update failed: ", e);
			return Optional.empty();
		}
	}

	public void remove(String taskId) {
		if("testId".equals(taskId)) {
			LOG.info("taskId = testId...");
			return;
		}
		LOG.info("Remove task:{} requested", taskId);
		Long id = Long.getLong(taskId);
		simpleTaskHibernateDao.delete(id);
	}
	
	@Autowired
	public void setSimpleTaskHibernateDao(SimpleTaskHibernateDao simpleTaskHibernateDao) {
		this.simpleTaskHibernateDao = simpleTaskHibernateDao;
	}
	
	@Autowired
	public void setBaseSender(BaseSender baseSender) {
		this.baseSender = baseSender;
	}

}
