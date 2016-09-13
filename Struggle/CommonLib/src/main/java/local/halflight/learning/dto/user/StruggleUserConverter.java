package local.halflight.learning.dto.user;

import java.util.stream.Collectors;

import local.halflight.learning.entity.struggleuser.RegistrationDetailsEntity;
import local.halflight.learning.entity.struggleuser.RoleEntity;
import local.halflight.learning.entity.struggleuser.UserEntity;

public class StruggleUserConverter {
	public static StruggleUser toDto(UserEntity entity) {
		StruggleUser user = new StruggleUser();
		if(entity.getPassword() != null) {
			user.setPassword(new String(entity.getPassword()));
		}
		user.setUsername(entity.getUsername());
		user.setRegistrationDetails(RegistrationDetailsEntity.toDto(entity.getDetails()));
		if (entity.getRoles() != null && !entity.getRoles().isEmpty()) {
			user.setRoles(entity.getRoles().stream().map(RoleEntity::toDto).collect(Collectors.toSet()));
		}
		return user;
	}

	public static UserEntity toEntity(StruggleUser user) {
		UserEntity entity = new UserEntity();
		if (user.getPassword() != null) {
			entity.setPassword(user.getPassword().getBytes());
		}
		entity.setUsername(user.getUsername());
		entity.setEmail(user.getEmail());
		entity.setDetails(RegistrationDetailsEntity.toEntity(user.getRegistrationDetails()));
		if (user.getRoles() != null && !user.getRoles().isEmpty()) {
			entity.setRoles(user.getRoles().stream().map(RoleEntity::toEntity).collect(Collectors.toSet()));
		}
		return entity;
	}
}
