package local.halflight.learning.webservice.validation;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import local.halflight.learning.dto.user.StruggleUser;
import local.halflight.learning.dto.user.StruggleUserRequest;
import local.halflight.learning.dto.validationerror.ValidationError;
import local.halflight.learning.dto.validationerror.ValidationErrorLevel;
import local.halflight.learning.dto.validationerror.ValidationErrorType;
import local.halflight.learning.validation.Validator;
import local.halflight.learning.validation.aspect.ValidationErrorHandlingStrategy;

@Component
public class StruggleUserValidator implements Validator<StruggleUserRequest> {

	@Autowired
	ValidationErrorHandlingStrategy errorHandlingStrategy;
	
	public Map<ValidationErrorLevel,ValidationError> validate(StruggleUserRequest rq) {
		Map<ValidationErrorLevel,ValidationError> errors = new HashMap<>();
		if (rq != null && rq.getPayload() != null) {
			validateUUID(errors, rq.getPayload());
		}		
		return errors;
	}

	private void validateUUID(Map<ValidationErrorLevel, ValidationError> errors, StruggleUser payload) {
//FIXME
		//		if (payload.getUserUUID() == null || payload.getUserUUID().isEmpty()) {
//			payload.setUserUUID(UUID.randomUUID().toString());
//			errors.put(ValidationErrorLevel.WARNING, new ValidationError(ValidationErrorType.USER_WARNING_UUID_IS_EMPTY));
//		}
	}


	public Map<ValidationErrorLevel,ValidationError> validateUpdate(StruggleUser existing, StruggleUser update) {
		Map<ValidationErrorLevel,ValidationError> errors = new HashMap<>();
		if (update != null && existing != null) {
			if(isStringsEmpty(existing.getUsername(), update.getUsername()) ) {
				errors.put(ValidationErrorLevel.ERROR, new ValidationError(ValidationErrorType.USER_ERROR_USERNAME_IS_EMPTY));
			}

		}
		return errors;
	}
	
	private boolean isStringsEmpty(String existing, String update) {
		return StringUtils.isEmpty(existing) && 
			 StringUtils.isEmpty(update);
	}

}
