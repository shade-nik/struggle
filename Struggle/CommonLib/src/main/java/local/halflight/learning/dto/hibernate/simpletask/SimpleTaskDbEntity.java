package local.halflight.learning.dto.hibernate.simpletask;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.google.common.base.MoreObjects;

import local.halflight.learning.dto.BaseHibernateDto;
import local.halflight.learning.dto.simpletask.TaskPriority;

@NamedQueries(value = {
		@NamedQuery(name = SimpleTaskDbEntity.FIND_BY_NAME,
				    query = "select t from SimpleTaskDbEntity t where t.taskName = :taskname") })
@NamedNativeQueries(value = {
		@NamedNativeQuery(name = SimpleTaskDbEntity.CHECK_BY_NAME, 
				          query = "select count(*) from simple_task_entry where taskname = :taskname") })
@Entity
@Table(name = "simple_task_entry"/*
									 * , uniqueConstraints =
									 * {@UniqueConstraint(columnNames =
									 * {"taskname"}, name =
									 * "uq_simple_task_taskname")}
									 */)
public class SimpleTaskDbEntity extends BaseHibernateDto {

	public static final String FIND_BY_NAME= "SimpleTaskDbEntity.findTaskByName";
	public static final String CHECK_BY_NAME = "checkEntity";

	private String taskName;
	private String taskDescription;
	private TaskPriority priority;
	private List<String> notes;
	private boolean isInProgress;

	public SimpleTaskDbEntity() {
	}

	@Column(name = "taskname", /* unique = true, */ nullable = false)
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Column(name = "task_description", unique = false, nullable = true)
	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}


	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "enum('LOW','NORMAL', 'HIGH')")
	public TaskPriority getPriority() {
		return priority;
	}

	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}

	@ElementCollection(fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL })
	@CollectionTable(name = "simple_task_notes", joinColumns = @JoinColumn(name = "id"))
	public List<String> getNotes() {
		return notes;
	}

	public void setNotes(List<String> notes) {
		this.notes = notes;
	}

	@Transient
	public boolean isInProgress() {
		return isInProgress;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("taskName", taskName)
				.add("taskDescription", taskDescription)
				.add("priority", priority)
				.add("notes", notes)
				.toString();
	}

}
