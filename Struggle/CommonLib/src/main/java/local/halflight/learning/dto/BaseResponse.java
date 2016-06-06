package local.halflight.learning.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;

import local.halflight.learning.dto.validationerror.ValidationError;
import local.halflight.learning.dto.validationerror.ValidationErrorLevel;
import local.halflight.learning.dto.validationerror.ValidationErrorType;

public class BaseResponse
<RQ extends GenericRequest<? extends Payload>> implements GenericResponse<RQ> {

	protected RQ request;
	protected Map<ValidationErrorLevel, ValidationError> validationErrors;
	
	@Override
	public RQ getRequest() {
		return request;
	}
  
	public void setRequest(RQ request) {
		this.request = request;
	}
	
	@Override
	public Optional<Long> getPayloadId() {
		if(request != null && request.getPayload() != null) {
			return Optional.of(request.getPayload().getId());
		}
		return Optional.empty();
	}

	@Override
	public Map<ValidationErrorLevel, ValidationError> getValidationErrors() {
		return validationErrors;
	}

	@Override
	public void setValidationErrors(Map<ValidationErrorLevel, ValidationError> validationErrors) {
		this.validationErrors = validationErrors;
	}
	
	public void addValidationError(ValidationErrorLevel level, ValidationErrorType errorType) {
		if (validationErrors == null) {
			validationErrors = new HashMap<>();
		}
		validationErrors.put(level, new ValidationError(errorType));
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("Request", request)
				.add("validationErrors", validationErrors)
				.toString();
	}

}
