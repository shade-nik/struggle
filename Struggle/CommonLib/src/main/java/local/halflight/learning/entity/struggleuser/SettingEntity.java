package local.halflight.learning.entity.struggleuser;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

import local.halflight.learning.dto.struggleuser.Role;
import local.halflight.learning.dto.struggleuser.Setting;
import local.halflight.learning.entity.BaseHibernateDto;

@Entity
@Table(name = "user_settings")
public class SettingEntity extends BaseHibernateDto {

	private String setting;

	private UserEntity user;

	public SettingEntity() {
	}

	@ManyToOne
	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public SettingEntity(String settingDescription) {
		this.setting = settingDescription;
	}

	public String getSetting() {
		return setting;
	}

	public void setSetting(String settingDescription) {
		this.setting = settingDescription;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("user", user)
				.add("setting", setting)
				.toString();
	}

//	public static Set<Setting> getDtoSet(Set<SettingEntity> entities) {
//	
//		Set<Setting> settings = new HashSet<>();
//		for (SettingEntity entity : entities) {
//			settings.add(toDto(entity));
//		}
//		return settings;
//	}

	public static Setting toDto(SettingEntity entity) {
		Setting setting = new Setting();
		setting.setSettingDescription(entity.getSetting());
		return setting;
		
	}

	public static SettingEntity toEntity(Setting setting) {
		SettingEntity entity = new SettingEntity();
		entity.setSetting(setting.getSettingDescription());
		return entity;
	}
}
