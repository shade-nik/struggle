package local.halflight.learning.entity.struggleuser;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

import local.halflight.learning.dto.struggleuser.Setting;
import local.halflight.learning.entity.BaseHibernateDto;

@Entity
@Table(name = "user_settings")
public class SettingEntity extends BaseHibernateDto {

	private String setting;

	private UserEntity user;

	public SettingEntity() {
	}
	public SettingEntity(String settingDescription) {
		this.setting = settingDescription;
	}
	@ManyToOne
	@JoinColumn(name="id", insertable=false, updatable=false)
	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
	@Column
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
				.add("user_name", user.getUsername())
				.add("setting", setting)
				.toString();
	}

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
