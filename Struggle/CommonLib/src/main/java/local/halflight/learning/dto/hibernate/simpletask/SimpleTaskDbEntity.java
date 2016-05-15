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

import com.google.common.base.MoreObjects;

import local.halflight.learning.dto.simpletask.TaskPriority;

@NamedQueries(value = { @NamedQuery(name = "findTaskByName", query = "from SimpleTaskDbEntity where taskname = :taskname") })
@NamedNativeQueries(value = {@NamedNativeQuery(name = "checkEntity", query = "select count(*) from simple_task_entry where taskname = :taskname")})
@Entity
@Table(name = "simple_task_entry"/*, uniqueConstraints = {@UniqueConstraint(columnNames = {"taskname"}, name = "uq_simple_task_taskname")}*/)
public class SimpleTaskDbEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "task_id", nullable = false, unique = true)
	private Integer id;
	
	@Column(name = "taskname", /*unique = true,*/ nullable = false)
	private String taskName;
	
	@Column(name = "task_description", unique = false, nullable = true)
	private String taskDescription;
	
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('LOW','NORMAL', 'HIGH')")
	private TaskPriority priority;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "simple_task_notes", joinColumns = @JoinColumn(name="id"))
	private List<String> notes;

	@Transient
	private boolean isInProgress;

	public SimpleTaskDbEntity() {
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public TaskPriority getPriority() {
		return priority;
	}

	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}

	public List<String> getNotes() {
		return notes;
	}

	public void setNotes(List<String> notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("taskName", taskName)
				.add("taskDescription", taskDescription)
				.toString();
	}
	
}
