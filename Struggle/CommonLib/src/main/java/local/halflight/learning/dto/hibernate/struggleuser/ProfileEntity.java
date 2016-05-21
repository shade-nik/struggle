package local.halflight.learning.dto.hibernate.struggleuser;

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

import local.halflight.learning.dto.BaseHibernateDto;
import local.halflight.learning.dto.struggleuser.Profile;


@Entity
@Table(name="user_profile")
public class ProfileEntity extends BaseHibernateDto {

	private UserEntity userEntity;
	private String firstName;
	private String lastName;
	private Gender gender;
	private Date registrationDate;
	private Date lastVisited;
	private boolean enabled = true;
	private byte[] image; //TODO extract to other table

	public ProfileEntity() {
	}
	
	public ProfileEntity(String fname, String lname, boolean enabled, Date nowDate) {
		this.firstName = fname;
		this.lastName = lname;
		this.enabled = enabled;
		this.registrationDate = nowDate;
		this.lastVisited = nowDate;
	}

	@OneToOne(mappedBy="profile")
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
	
	@Lob
    @Column(length=32*1024*1024, columnDefinition="longblob", name="image_data") 
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
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
				.add("image", image)
				.toString();
	}

	public static Profile getDto(ProfileEntity entity) {
		Profile profile = new Profile();
		profile.setEnabled(entity.isEnabled());
		profile.setFirstName(entity.getFirstName());
		profile.setLastName(entity.getLastName());
		profile.setGender(entity.getGender());
		profile.setImage(entity.getImage());
		profile.setLastVisited(entity.getLastVisited());
		profile.setRegistrationDate(entity.getRegistrationDate());
		return profile;
	}

}
