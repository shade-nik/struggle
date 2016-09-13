package local.halflight.learning.entity.struggleuser;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.google.common.base.MoreObjects;

import local.halflight.learning.entity.BaseHibernateDto;

@NamedQueries(value = {
		@NamedQuery(name = "findUserByName", query = "from UserEntity where username = :username"),
		@NamedQuery(name = "findUserByEmail", query = "from UserEntity where email = :email") })
@Entity
@Table(name = "user", uniqueConstraints = { 
		@UniqueConstraint(columnNames = { "username" }, name = "uq_user_username"),
		@UniqueConstraint(columnNames = { "email" }, name = "uq_user_email") })
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
public class UserEntity extends BaseHibernateDto {

	public static final String FIND_BY_NAME = "UserEntity.findUserByName";
	public static final String FIND_BY_UUID = "UserEntity.findUserByEmail";

	private String email;
	private String username;
	private byte[] password;
    private RegistrationDetailsEntity details;
	private Set<RoleEntity> roles;

	public UserEntity(String username, byte[] password) {
		this.username = username;
		this.password = password;
	}

	public UserEntity() {
	}
	
	@Column(name = "email", unique = true, nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "username", nullable = false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}


	@OneToOne(cascade = { CascadeType.ALL }, optional = false)
	@JoinColumn(name = "details_id", unique = true, nullable = false, foreignKey = @ForeignKey(name = "fk_user_details"))
	public RegistrationDetailsEntity getDetails() {
		return details;
	}

	public void setDetails(RegistrationDetailsEntity details) {
		this.details = details;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinTable(name = "user_role_xref", 
	           joinColumns = @JoinColumn(
	        		    name = "xref_user_id", 
	           			referencedColumnName = "user_id",
	           			foreignKey = @ForeignKey(name = "fk_user")),
	                    foreignKey = @ForeignKey(name = "fk_out_user"),
	           inverseJoinColumns = @JoinColumn(name = "xref_role_id",
	           			referencedColumnName = "user_role_id",
	           			foreignKey = @ForeignKey(name = "fk_security_role")),
	                    inverseForeignKey = @ForeignKey(name = "fk_out_security_role"))
	public Set<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("email", email)
				.add("username", username)
				.add("password", password)
				.add("details", details)
				.add("roles", roles).toString();
	}
}
