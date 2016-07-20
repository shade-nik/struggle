package local.halflight.learning.entity.struggleuser;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

import local.halflight.learning.dto.struggleuser.Role;
import local.halflight.learning.entity.BaseHibernateDto;

@Entity
@Table(name = "user_role")
@AttributeOverride(name = "id", column = @Column(name = "user_role_id"))
public class RoleEntity extends BaseHibernateDto {

	private String role;

	private String description;

	@Column
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Column
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
	
	public static Role toDto(RoleEntity entity) {
		Role role = new Role();
		role.setDescription(entity.getDescription());
		role.setRole(entity.getRole());
		return role;
	}

	public static RoleEntity toEntity(Role role) {
		RoleEntity entity = new RoleEntity();
		entity.setDescription(role.getDescription());
		entity.setRole(role.getRole());
		return entity;
	}
}
