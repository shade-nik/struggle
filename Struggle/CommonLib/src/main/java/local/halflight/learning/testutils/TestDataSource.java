package local.halflight.learning.testutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import local.halflight.learning.dto.hibernate.simpletask.SimpleTaskDbEntity;
import local.halflight.learning.dto.simpletask.SimpleTask;
import local.halflight.learning.dto.simpletask.TaskPriority;

public class TestDataSource {
	
	private static final String TASK_NAME = "Test task name";
	private static final String TASK_DESCRIPTION= "Test task descritpion";
	private static final Integer TASK_ID = 12456;
	private static final TaskPriority PRIORITY= TaskPriority.HIGH;
	private static final List<String> NOTES = Arrays.asList("Note1", "Note12");
	private static final boolean IS_INPROGRESS = true;
	
	public static SimpleTask generateTask() {
		return generateTask("1");
	}

	public static SimpleTask generateTask(String id) {
		SimpleTask task = new SimpleTask();
		task.setTaskName(TASK_NAME + id);
		task.setTaskDescription(TASK_DESCRIPTION + id);
		task.setPriority(PRIORITY);
		task.setTaskId(TASK_ID);
		task.setNotes(NOTES);
		return task;
	}
	
	public static SimpleTaskDbEntity generateSimpleTaskDbEntity(Integer id) {
		SimpleTaskDbEntity entity = new SimpleTaskDbEntity();
		entity.setTaskName(TASK_NAME);
		entity.setTaskDescription(TASK_DESCRIPTION);
		entity.setPriority(PRIORITY);
		entity.setNotes(NOTES);
		return entity;
	}

	
	public static List<SimpleTask> generateTaskList(int size) {
		List<SimpleTask> list = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			list.add(generateTask(String.valueOf(i)));
		}
		return list;
	}

}

