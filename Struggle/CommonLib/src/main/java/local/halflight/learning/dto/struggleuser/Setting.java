package local.halflight.learning.dto.struggleuser;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Setting {

	
	@XmlElement(name="Description")
	private String settingDescription;


	public Setting() {
	}

	public Setting(String settingDescription) {
		this.settingDescription = settingDescription;
	}

	public String getSettingDescription() {
		return settingDescription;
	}

	public void setSettingDescription(String settingDescription) {
		this.settingDescription = settingDescription;
	}

}