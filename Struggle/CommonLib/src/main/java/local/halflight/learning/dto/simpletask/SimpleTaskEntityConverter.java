package local.halflight.learning.dto.simpletask;

import local.halflight.learning.dto.DtoConverter;
import local.halflight.learning.dto.hibernate.simpletask.SimpleTaskDbEntity;

public class SimpleTaskEntityConverter extends DtoConverter {

	public static SimpleTask toDto(SimpleTaskDbEntity entity) {
		if (entity == null)
			return null;
		SimpleTask dto = new SimpleTask();
		dto.setTaskName(entity.getTaskName());
		dto.setTaskDescription(entity.getTaskDescription());
		dto.setPriority(entity.getPriority());
		dto.setTaskId(entity.getId());
		dto.setNotes(entity.getNotes());
		return dto;
	}

	public static SimpleTaskDbEntity toEntity(SimpleTask dto) {
		if (dto == null)
			return null;
		SimpleTaskDbEntity entity = new SimpleTaskDbEntity();
		entity.setTaskName(dto.getTaskName());
		entity.setTaskDescription(dto.getTaskDescription());
		entity.setPriority(dto.getPriority());
		 entity.setId(dto.getId());
		entity.setNotes(dto.getNotes());
		return entity;
	}

}
