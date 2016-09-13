package local.halflight.learning.dto.role;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.MoreObjects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Role {
	
	@XmlElement
	private String name;
	
	@XmlElement
	private String description;

	public String getRole() {
		return name;
	}

	public void setRole(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Role() {
	}

	public Role(String name, String desc) {
		this.name = name;
		this.description = desc;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("name", name)
				.add("description", description)
				.toString();
	}
}	
