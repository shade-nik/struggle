package local.halflight.learning.entity.struggleuser;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.common.base.MoreObjects;

import local.halflight.learning.dto.user.RegistrationDetails;
import local.halflight.learning.entity.BaseHibernateDto;


@Entity
@Table(name="user_registration_details")
public class RegistrationDetailsEntity extends BaseHibernateDto {

	private UserEntity userEntity;
	private String firstName;
	private String lastName;
	private Gender gender;
	private Date registrationDate;
	private Date lastVisited;
	private boolean enabled = true;

	public RegistrationDetailsEntity() {
	}
	
	public RegistrationDetailsEntity(String fname, String lname, boolean enabled, Date nowDate) {
		this.firstName = fname;
		this.lastName = lname;
		this.enabled = enabled;
		this.registrationDate = nowDate;
		this.lastVisited = nowDate;
	}

	@OneToOne(mappedBy="details")
	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	
    @Enumerated(EnumType.STRING)
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

@Column	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

@Column	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastVisited() {
		return lastVisited;
	}

	public void setLastVisited(Date lastVisited) {
		this.lastVisited = lastVisited;
	}
@Column
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("firstName", firstName)
				.add("lastName", lastName)
				.add("enabled", enabled)
				.add("registrationDate", registrationDate)
				.add("lastVisited", lastVisited)
				.toString();
	}

	public static RegistrationDetails toDto(RegistrationDetailsEntity entity) {
		RegistrationDetails profile = new RegistrationDetails();
		profile.setEnabled(entity.isEnabled());
		profile.setFirstName(entity.getFirstName());
		profile.setLastName(entity.getLastName());
		profile.setGender(entity.getGender());
		profile.setLastVisited(entity.getLastVisited());
		profile.setRegistrationDate(entity.getRegistrationDate());
		return profile;
	}

	public static RegistrationDetailsEntity toEntity(RegistrationDetails profile) {
		RegistrationDetailsEntity entity = new RegistrationDetailsEntity();
		entity.setEnabled(profile.isEnabled());
		entity.setFirstName(profile.getFirstName());
		entity.setLastName(profile.getLastName());
		entity.setGender(profile.getGender());
		entity.setLastVisited(profile.getLastVisited());
		entity.setRegistrationDate(profile.getRegistrationDate());
		return entity;
	}

}
