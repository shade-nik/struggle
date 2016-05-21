package local.halflight.learning.dto;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class BaseHibernateDto {
	//TODO place common base fields here
	//might be overriden with entity level annotation 
	//	@AttributeOverride( name="super_name", column = @Column(name="overr_name") )

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, unique = true)
	protected Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	protected Calendar createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Calendar createDate) {
		this.createDate = createDate;
	}
	
	
}
