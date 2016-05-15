package local.halflight.learning.dto.simpletask;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.MoreObjects;

import local.halflight.learning.dto.validationerror.ValidationError;
import local.halflight.learning.dto.validationerror.ValidationErrorType;

@XmlRootElement(name = "SimpleTaskResponse")
@XmlAccessorType(XmlAccessType.NONE)
public class SimpleTaskResponse {

	@XmlElement(name = "SimpleTaskRequest")
	private SimpleTaskRequest request;

	@XmlElement(name = "SimpleTaskPayload")
	private SimpleTask payload;
	
	@XmlElementWrapper(name = "ValidationErrors")
	@XmlElement(name = "ValidationError")
	Set<ValidationError> validationErrors;
	
	public SimpleTaskResponse() {
	}
	
	public SimpleTaskResponse(SimpleTask payload) {
		this.payload = payload;
	}

	public SimpleTaskResponse(SimpleTaskRequest request) {
		this.request = request;
	}
	public SimpleTaskRequest getRequest() {
		return request;
	}

	public void setRequest(SimpleTaskRequest request) {
		this.request = request;
	}
	
	public SimpleTask getPayload() {
		return payload;
	}

	public void setPayload(SimpleTask payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("SimpleTaskRequest", request)
				.add("SimpleTask", payload)
				.toString();
	}

	public void addValidationError(ValidationErrorType errorType) {
		if (validationErrors == null) {
			validationErrors = new HashSet<>();
		}
		validationErrors.add(new ValidationError(errorType));
	}
}
