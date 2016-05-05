package local.halflight.learning.webservice.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SimpleTaskService {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleTaskService.class);

	public SimpleTaskService() {
	}

	public List<String> getTaskList() {
		
		List<String> list = new ArrayList<>();
		for(int i = 0; i < 5; i++) {
			list.add("TaskIndex" + i);
		}
		
		return list;
	}

	public String findString(String taskId) {
		return "FoundedItem";
	}

}
