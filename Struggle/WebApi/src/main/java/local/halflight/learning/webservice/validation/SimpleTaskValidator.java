package local.halflight.learning.webservice.validation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import local.halflight.learning.dto.simpletask.SimpleTask;
import local.halflight.learning.dto.simpletask.SimpleTaskRequest;
import local.halflight.learning.dto.validationerror.ValidationError;
import local.halflight.learning.dto.validationerror.ValidationErrorLevel;
import local.halflight.learning.dto.validationerror.ValidationErrorType;


@Component
public class SimpleTaskValidator extends BaseValidator<SimpleTaskRequest> {
	@Override
	public Map<ValidationErrorLevel,ValidationError> validate(SimpleTaskRequest request) {

		Map<ValidationErrorLevel,ValidationError> errors = new HashMap<>();
		
		SimpleTask payload = request.getPayload() != null ? request.getPayload() : null;
		if(payload != null ) { 
			validateSimpleTask(payload);
		}
		else {
			errors.put(ValidationErrorLevel.ERROR, new ValidationError(ValidationErrorType.VALIDATION_ERROR));
		}
		
		return errors;
	}

	private void validateSimpleTask(SimpleTask payload) {
	}
	
}
