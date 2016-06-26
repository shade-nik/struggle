package local.halflight.learning.dto.struggleuser;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.MoreObjects;

import local.halflight.learning.entity.struggleuser.Gender;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Profile {
	@XmlElement
	private String firstName;
	@XmlElement
	private String lastName;
	@XmlElement
	private Gender gender;
	@XmlElement
	private Date registrationDate;
	@XmlElement
	private Date lastVisited;
	@XmlElement
	private boolean enabled = true;
	private byte[] image;

	public Profile() {
	}
	
	public Profile(String fname, String lname, boolean enabled, Date nowDate) {
		this.firstName = fname;
		this.lastName = lname;
		this.enabled = enabled;
		this.registrationDate = nowDate;
		this.lastVisited = nowDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Date getLastVisited() {
		return lastVisited;
	}

	public void setLastVisited(Date lastVisited) {
		this.lastVisited = lastVisited;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("firstName", firstName)
				.add("lastName", lastName)
				.add("enabled", enabled)
				.add("registrationDate", registrationDate)
				.add("lastVisited", lastVisited)
				.add("gender", gender)
				.add("image", image)
				.toString();
	}
	
}
