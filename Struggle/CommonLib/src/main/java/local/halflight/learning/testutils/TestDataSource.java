package local.halflight.learning.testutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import local.halflight.learning.dto.simpletask.SimpleTask;
import local.halflight.learning.dto.simpletask.TaskPriority;
import local.halflight.learning.entity.simpletask.SimpleTaskDbEntity;
import local.halflight.learning.entity.struggleuser.ProfileEntity;
import local.halflight.learning.entity.struggleuser.RoleEntity;
import local.halflight.learning.entity.struggleuser.SettingEntity;
import local.halflight.learning.entity.struggleuser.UserEntity;

public class TestDataSource {

	private static final String TASK_NAME = "Test task name";
	private static final String TASK_DESCRIPTION = "Test task descritpion";
	private static final Long TASK_ID = 12456l;
	private static final TaskPriority PRIORITY = TaskPriority.HIGH;
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

	public static SimpleTaskDbEntity generateSimpleTaskDbEntity(Long id) {

		SimpleTaskDbEntity entity = new SimpleTaskDbEntity();
		entity.setTaskName(TASK_NAME);
		entity.setTaskDescription(TASK_DESCRIPTION);
		entity.setPriority(PRIORITY);
		entity.setNotes(NOTES);
		entity.setId(id);
		return entity;
	}

	public static class User {

		public static UserEntity generateUser(String username) {
			UserEntity user = new UserEntity();
			user.setUsername(username);
			user.setPassword("Password".getBytes());
			user.setCreateDate(Calendar.getInstance());
			user.setUserUUID(UUID.randomUUID().toString());

			user.setProfile(generateProfile(username));
			user.setSettings(generateSettings(4));
			user.setGroups(generateGroups());
			user.setRoles(generateRoles());
			return user;
		}

		public static ProfileEntity generateProfile(String username) {
			ProfileEntity profile = new ProfileEntity();
			profile.setCreateDate(Calendar.getInstance());
			profile.setRegistrationDate(new Date());
			profile.setEnabled(true);
			profile.setFirstName(username);
			profile.setLastName("d'" + username);
			profile.setLastVisited(new Date());
			return profile;
		}

		public static SettingEntity generateSetting(String settingDescr) {
			SettingEntity setting = new SettingEntity(settingDescr);

			return setting;
		}

		public static Set<SettingEntity> generateSettings(int num) {
			HashSet<SettingEntity> res = new HashSet<>();
			for (int i = 0; i < num; ++i) {
				res.add(generateSetting("Description: " + i));
			}

			return res;
		}

		public static Set<String> generateGroups() {
			HashSet<String> groups = new HashSet<>();
			groups.add("FRST_GRP");
			groups.add("SEC_GRP");
			groups.add("THRD_GRP");

			return groups;
		}

		public static Set<RoleEntity> generateRoles() {
			HashSet<RoleEntity> roles = new HashSet<>();
			roles.add(new RoleEntity("USER", "user role descr"));
			roles.add(new RoleEntity("ADMIN", "admin role descr"));
			return roles;
		}

	}

	public static List<SimpleTask> generateTaskList(int size) {
		List<SimpleTask> list = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			list.add(generateTask(String.valueOf(i)));
		}
		return list;
	}


}
