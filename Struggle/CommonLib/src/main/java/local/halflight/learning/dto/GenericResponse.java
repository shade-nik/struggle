package local.halflight.learning.dto;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import local.halflight.learning.dto.validationerror.ValidationError;
import local.halflight.learning.dto.validationerror.ValidationErrorLevel;

public interface GenericResponse<RQ extends GenericRequest<? extends Payload>> {
	
	Map<ValidationErrorLevel, ValidationError> getValidationErrors();

	void setValidationErrors(Map<ValidationErrorLevel, ValidationError> validationErrors);
	
	RQ getRequest();

	void setRequest(RQ request);
	
	Optional<Long> getPayloadId();
}
