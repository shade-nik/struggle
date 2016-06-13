package local.halflight.learning.dto.hibernate.struggleuser;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

import local.halflight.learning.dto.BaseHibernateDto;
import local.halflight.learning.dto.struggleuser.StruggleUser;

@NamedQueries(value = { @NamedQuery(name = "findUserByName", query = "from UserEntity where username = :username"),
		@NamedQuery(name = "findUserByUUID", query = "from UserEntity where userUUID = :userUUID") })
@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }, name = "uq_user_username"),
		@UniqueConstraint(columnNames = { "userUUID" }, name = "uq_user_uuid") })
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
public class UserEntity extends BaseHibernateDto {

	public static final String FIND_BY_NAME = "UserEntity.findUserByName";
	public static final String FIND_BY_UUID = "UserEntity.findUserByUUID";

	private String userUUID;
	private String username;
	private byte[] password;
	private Set<SettingEntity> settings;
	private ProfileEntity profile;
	private Set<String> groups;
	private Set<RoleEntity> roles;

	public UserEntity(String username, byte[] password) {
		this.username = username;
		this.password = password;
	}

	public UserEntity() {
		// TODO Auto-generated constructor stub
	}

	@Column(name = "userUUID", unique = true, nullable = false)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	public String getUserUUID() {
		return userUUID;
	}

	public void setUserUUID(String userUUID) {
		this.userUUID = userUUID;
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

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	public Set<SettingEntity> getSettings() {
		return settings;
	}

	public void setSettings(Set<SettingEntity> settings) {
		this.settings = settings;
	}

	@OneToOne(cascade = { CascadeType.ALL }, optional = false)
	@JoinColumn(name = "profile_id", unique = true, nullable = false, foreignKey = @ForeignKey(name = "fk_user_profile"))
	public ProfileEntity getProfile() {
		return profile;
	}

	public void setProfile(ProfileEntity profile) {
		this.profile = profile;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "user_group", joinColumns = @JoinColumn(name = "id"))
	public Set<String> getGroups() {
		return groups;
	}

	public void setGroups(Set<String> groups) {
		this.groups = groups;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinTable(name = "user_role_xref", joinColumns = @JoinColumn(name = "xref_user_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "fk_user")), foreignKey = @ForeignKey(name = "fk_out_user"), inverseJoinColumns = @JoinColumn(name = "xref_role_id", referencedColumnName = "user_role_id", foreignKey = @ForeignKey(name = "fk_security_role")), inverseForeignKey = @ForeignKey(name = "fk_out_security_role"))
	public Set<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("id", id).add("userUUID", userUUID).add("username", username)
				.add("password", password).add("settings", settings).add("profile", profile).add("groups", groups)
				.add("roles", roles).toString();
	}
}
