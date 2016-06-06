package local.halflight.learning.dto.simpletask;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.MoreObjects;

import local.halflight.learning.dto.BaseResponse;
import local.halflight.learning.dto.validationerror.ValidationError;
import local.halflight.learning.dto.validationerror.ValidationErrorLevel;

@XmlRootElement(name = "SimpleTaskResponse")
@XmlAccessorType(XmlAccessType.NONE)
public class SimpleTaskResponse extends BaseResponse<SimpleTaskRequest> {

	private SimpleTask payload;
	
	public SimpleTaskResponse() {
	}
	
	public SimpleTaskResponse(SimpleTask payload) {
		this.payload = payload;
	}

	public SimpleTaskResponse(SimpleTaskRequest request) {
		this.request = request;
	}
	
	@XmlElement(name = "SimpleTaskRequest")
	public SimpleTaskRequest getRequest() {
		return request;
	}

	public void setRequest(SimpleTaskRequest request) {
		this.request = request;
	}
	
	@XmlElement(name = "SimpleTaskPayload")
	public SimpleTask getPayload() {
		return payload;
	}

	public void setPayload(SimpleTask payload) {
		this.payload = payload;
	}
	
	@Override
	@XmlElement(name = "ValidatinErrors")
	public Map<ValidationErrorLevel, ValidationError> getValidationErrors() {
		return super.getValidationErrors();
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("SimpleTaskRequest", request)
				.add("SimpleTask", payload)
				.add("validationErrors", validationErrors)
				.toString();
	}
}
