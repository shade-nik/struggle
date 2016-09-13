package local.halflight.learning.dto.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.common.base.MoreObjects;

import local.halflight.learning.dto.Payload;
import local.halflight.learning.dto.role.Role;

@XmlRootElement(name = "StruggleUserPayload")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = { "username", "email", "password", "registrationDetails", "roles" })
public class StruggleUser implements Payload, UserDetails {

	private static final long serialVersionUID = 6825474579252728958L;

	private Long id;
	private String username;
	private String email;
	private String password;
	
	private RegistrationDetails registrationDetails;
	private Set<Role> roles;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlElement
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@XmlElement
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	@XmlElement
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@XmlElementWrapper(name = "Roles")
	@XmlElement(name = "Role")
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@XmlElement
	public RegistrationDetails getRegistrationDetails() {
		return registrationDetails;
	}

	public void setRegistrationDetails(RegistrationDetails details) {
		this.registrationDetails = details;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("username", username)
				.add("email", email)
				.add("password", "[secret]")
				.add("details", registrationDetails)
				.add("roles", roles)
				.toString();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<SimpleGrantedAuthority> autorities = new HashSet<>();
		for(Role role : getRoles()) {
			autorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
		}
		return autorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return getRegistrationDetails().isEnabled();
	}

}
