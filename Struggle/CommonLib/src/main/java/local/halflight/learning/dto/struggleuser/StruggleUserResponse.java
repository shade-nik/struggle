package local.halflight.learning.dto.struggleuser;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.MoreObjects;

import local.halflight.learning.dto.BaseResponse;
import local.halflight.learning.dto.validationerror.ValidationError;
import local.halflight.learning.dto.validationerror.ValidationErrorLevel;

@XmlRootElement(name = "StruggleUserResponse")
@XmlAccessorType(XmlAccessType.NONE)
public class StruggleUserResponse extends BaseResponse<StruggleUserRequest> {

	private StruggleUserRequest request;

	@XmlElement(name = "StruggleUserPayload")
	private StruggleUser payload;

	public StruggleUserResponse() {
	}

	public StruggleUserResponse(StruggleUser payload) {
		this.payload = payload;
	}
	
	public StruggleUserResponse(StruggleUserRequest request) {
		this.request = request;
	}

	@XmlElement(name = "StruggleUserRequest")
	public StruggleUserRequest getRequest() {
		return request;
	}

	public void setRequest(StruggleUserRequest request) {
		this.request = request;
	}

	public StruggleUser getPayload() {
		return payload;
	}

	public void setPayload(StruggleUser payload) {
		this.payload = payload;
	}

	
	
	@XmlElement
	@XmlElementWrapper(name="ValidationErrors")
	@Override
	public Map<ValidationErrorLevel, ValidationError> getValidationErrors() {
		// TODO Auto-generated method stub
		return super.getValidationErrors();
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("StruggleUserRequest", request).add("StruggleUser", payload)
				.add("validationErrors", validationErrors).toString();
	}
}
