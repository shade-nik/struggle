package local.halflight.learning.webservice.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import local.halflight.learning.dto.simpletask.SimpleTask;
import local.halflight.learning.testutils.TestDataSource;

@Service
public class SimpleTaskService {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleTaskService.class);

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
		List<SimpleTask> taskList = TestDataSource.generateTaskList(4);
		LOG.info("Found tasks: {}", taskList);
		return taskList;
	}

	public SimpleTask findTask(String taskId) {

		SimpleTask task = TestDataSource.generateTask();
		LOG.info("Found task: {}", task);

		return task;

	}
	

}
