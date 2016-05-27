package local.halflight.learning.dto.struggleuser;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.google.common.base.MoreObjects;

@XmlRootElement(name="StruggleUserPayload")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder={"username", "password", "userUUID", "profile", "settings", "roles", "groups"})
public class StruggleUser {
	
	private static final long serialVersionUID = 6825474579252728958L;

	private String userUUID;
	private String username;
	private String password;
	private Profile profile; 
	private Set<Setting> settings; 
	private Set<Role> roles;
	private Set<String> groups;
	
	@XmlElement	
	public String getUserUUID() {
		return userUUID;
	}
	public void setUserUUID(String userUUID) {
		this.userUUID = userUUID;
	}
	@XmlElement
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@XmlElement
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@XmlElement
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	@XmlElementWrapper(name="Settings")
	@XmlElement(name="Setting")
	public Set<Setting> getSettings() {
		return settings;
	}
	public void setSettings(Set<Setting> settings) {
		this.settings = settings;
	}
	
	@XmlElementWrapper(name="Roles")
	@XmlElement(name="Role")
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	@XmlElementWrapper(name="Groups")
	@XmlElement(name="Group")
	public Set<String> getGroups() {
		return groups;
	}
	public void setGroups(Set<String> groups) {
		this.groups = groups;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("userUUID", userUUID)
				.add("username", username)
				.add("password", "[secret]")
				.add("profile", profile)
				.add("settings", settings)
				.add("roles",roles )
				.add("groups",groups )

				.toString();
	}

}
