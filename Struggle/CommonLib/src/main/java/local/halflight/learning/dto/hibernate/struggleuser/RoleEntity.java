package local.halflight.learning.dto.hibernate.struggleuser;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.google.common.base.MoreObjects;

import local.halflight.learning.dto.BaseHibernateDto;
import local.halflight.learning.dto.struggleuser.Role;

@Entity
@Table(name = "user_role")
// , uniqueConstraints = @UniqueConstraint(columnNames={"role", "user_id"}))
@AttributeOverride(name = "id", column = @Column(name = "user_role_id"))
public class RoleEntity extends BaseHibernateDto {

	private String role;

	private String description;

	@Column
	public String getRole() {
		return role;
	}

	@Column
	public void setRole(String role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RoleEntity() {
	}

	public RoleEntity(String role, String desc) {
		this.role = role;
		this.description = desc;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("id", id).add("role", role).add("description", description)
				.toString();
	}

	public static Set<Role> getDtoSet(Set<RoleEntity> entities) {
		Set<Role> roles = new HashSet<>();
		for (RoleEntity entity : entities) {
			roles.add(getDto(entity));
		}
		return roles;
	}

	public static Role getDto(RoleEntity entity) {
		Role role = new Role();
		role.setDescription(entity.getDescription());
		role.setRole(entity.getRole());
		return role;
	}
}
