package local.halflight.learning.webservice.validation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import local.halflight.learning.dto.simpletask.SimpleTask;
import local.halflight.learning.dto.simpletask.SimpleTaskRequest;
import local.halflight.learning.dto.validationerror.ValidationError;
import local.halflight.learning.dto.validationerror.ValidationErrorLevel;
import local.halflight.learning.dto.validationerror.ValidationErrorType;
import local.halflight.learning.model.TaskType;
import local.halflight.learning.validation.BaseWebRequestValidator;

@Component
public class SimpleTaskValidator extends BaseWebRequestValidator<SimpleTaskRequest> {

	@Override
	public Map<ValidationErrorLevel, ValidationError> validate(SimpleTaskRequest request) {

		Map<ValidationErrorLevel, ValidationError> errors = new HashMap<>();

		SimpleTask payload = request.getPayload() != null ? request.getPayload() : null;
		if (payload != null) {
			validateSimpleTask(payload, errors);
		} else {
			errors.put(ValidationErrorLevel.ERROR, new ValidationError(ValidationErrorType.VALIDATION_ERROR));
		}

		return errors;
	}

	private void validateSimpleTask(SimpleTask payload, Map<ValidationErrorLevel, ValidationError> errors) {
		if (TaskType.LONG.equals(payload.getTaskType()) || TaskType.REMOTE.equals(payload.getTaskType())) {
			errors.put(ValidationErrorLevel.WARNING, new ValidationError(ValidationErrorType.FEATURE_NOT_IMPLEMENTED,
					"TaskType", payload.getTaskType()));
		}
	}

}
