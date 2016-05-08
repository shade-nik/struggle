package local.halflight.learning.dto.simpletask;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(String.class)    
public enum TaskPriority {

	@XmlEnumValue("Low") LOW,
	@XmlEnumValue("Normal") NORMAL,
	@XmlEnumValue("High") HIGH;
}
