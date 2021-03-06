package local.halflight.learning.dto.simpletask;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.google.common.base.MoreObjects;

import local.halflight.learning.dto.Payload;
import local.halflight.learning.model.handlers.tasks.TaskType;

@XmlRootElement(name = "SimpleTask")
@XmlType(name = "SimpleTask")
@XmlAccessorType(XmlAccessType.NONE)
public class SimpleTask implements Payload {

	private String taskName;
	private TaskType taskType;
	private String taskDescription;
	private Long id;
	private TaskPriority priority;

	private List<String> notes;

	@XmlTransient
	private boolean isInProgress;
	
	
	@XmlElement(name = "TaskName")
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	@XmlElement(name = "TaskType")
	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	@XmlElement(name = "Description")
	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	@XmlElement(name = "Id")
	public Long getId() {
		return id;
	}

	public void setTaskId(Long id) {
		this.id = id;
	}

	@XmlElement(name = "Priority")
	public TaskPriority getPriority() {
		return priority;
	}

	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}
	
	@XmlElementWrapper(name = "Notes")
	@XmlElement(name = "Note")
	public List<String> getNotes() {
		return notes;
	}

	public void setNotes(List<String> notes) {
		this.notes = notes;
	}

	public boolean isInProgress() {
		return isInProgress;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("taskName", taskName)
				.add("taskType", taskType)
				.add("taskDescription", taskDescription)
				.add("id", id)
				.add("priority", priority)
				.add("notes", notes)
				.add("isInProgress", isInProgress)
				.toString();
	}
	
}
