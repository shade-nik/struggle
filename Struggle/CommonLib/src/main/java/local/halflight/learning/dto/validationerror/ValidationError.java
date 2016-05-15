package local.halflight.learning.dto.validationerror;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class ValidationError  {

	@XmlElement
	private Integer code;

	@XmlElement
	private String descripiton;
	
	public ValidationError() {
	}
	
	public ValidationError(ValidationErrorType type) {
		this.descripiton = type.getDescription();
		this.code = type.getCode();
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDescripiton() {
		return descripiton;
	}

	public void setDescripiton(String descripiton) {
		this.descripiton = descripiton;
	}
	
	
}
