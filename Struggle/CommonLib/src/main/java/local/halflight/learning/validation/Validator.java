package local.halflight.learning.validation;

import java.util.Map;

import local.halflight.learning.dto.GenericRequest;
import local.halflight.learning.dto.Payload;
import local.halflight.learning.dto.validationerror.ValidationError;
import local.halflight.learning.dto.validationerror.ValidationErrorLevel;

public interface Validator<T> {
	
	 Map<ValidationErrorLevel, ValidationError> validate(T request);

}
