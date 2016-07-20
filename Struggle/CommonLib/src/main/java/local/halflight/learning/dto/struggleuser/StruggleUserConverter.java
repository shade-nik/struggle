package local.halflight.learning.dto.struggleuser;

import java.util.stream.Collectors;

import local.halflight.learning.entity.struggleuser.ProfileEntity;
import local.halflight.learning.entity.struggleuser.RoleEntity;
import local.halflight.learning.entity.struggleuser.SettingEntity;
import local.halflight.learning.entity.struggleuser.UserEntity;

public class StruggleUserConverter {
	public static StruggleUser toDto(UserEntity entity) {
		StruggleUser user = new StruggleUser();
		if(entity.getPassword() != null) {
			user.setPassword(new String(entity.getPassword()));
		}
		user.setUsername(entity.getUsername());
		user.setUserUUID(entity.getUserUUID());
		user.setProfile(ProfileEntity.toDto(entity.getProfile()));
		if (entity.getRoles() != null && !entity.getRoles().isEmpty()) {
			user.setRoles(entity.getRoles().stream().map(RoleEntity::toDto).collect(Collectors.toSet()));
		}
		if (entity.getSettings() != null && !entity.getRoles().isEmpty()) {
			user.setSettings(entity.getSettings().stream().map(SettingEntity::toDto).collect(Collectors.toSet()));
		}
		user.setGroups(entity.getGroups());
		return user;
	}

	public static UserEntity toEntity(StruggleUser user) {
		UserEntity entity = new UserEntity();
		if (user.getPassword() != null) {
			entity.setPassword(user.getPassword().getBytes());
		}
		entity.setUsername(user.getUsername());
		entity.setUserUUID(user.getUserUUID());
		entity.setProfile(ProfileEntity.toEntity(user.getProfile()));
		if (user.getRoles() != null && !user.getRoles().isEmpty()) {
			entity.setRoles(user.getRoles().stream().map(RoleEntity::toEntity).collect(Collectors.toSet()));
		}
		if (user.getSettings() != null && !user.getSettings().isEmpty()) {
			entity.setSettings(user.getSettings().stream().map(SettingEntity::toEntity).collect(Collectors.toSet()));
		}
		entity.setGroups(user.getGroups());
		return entity;
	}
}
