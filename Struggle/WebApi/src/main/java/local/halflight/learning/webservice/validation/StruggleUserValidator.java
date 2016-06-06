package local.halflight.learning.webservice.validation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import local.halflight.learning.dto.struggleuser.StruggleUser;
import local.halflight.learning.dto.struggleuser.StruggleUserRequest;
import local.halflight.learning.dto.validationerror.ValidationError;
import local.halflight.learning.dto.validationerror.ValidationErrorLevel;
import local.halflight.learning.dto.validationerror.ValidationErrorType;

@Component
public class StruggleUserValidator extends BaseValidator<StruggleUserRequest> {

	public Map<ValidationErrorLevel,ValidationError> validate(StruggleUserRequest rq) {
		Map<ValidationErrorLevel,ValidationError> errors = new HashMap<>();
		if (rq != null && rq.getPayload() != null) {
			if (rq.getPayload().getUserUUID() == null) {
				rq.getPayload().setUserUUID(UUID.randomUUID().toString());
			}
		}
		
		return errors;
	}

	public Map<ValidationErrorLevel,ValidationError> validateUpdate(StruggleUser existing, StruggleUser update) {
		Map<ValidationErrorLevel,ValidationError> errors = new HashMap<>();
		if (update != null && existing != null) {
			if(isStringsEmpty(existing.getUsername(), update.getUsername()) ) {
				errors.put(ValidationErrorLevel.ERROR, new ValidationError(ValidationErrorType.USER_ERROR_USERNAME_IS_EMPTY));
			}
			if(isStringsEmpty(existing.getUserUUID(), update.getUserUUID()) ) {
				errors.put(ValidationErrorLevel.WARNING, new ValidationError(ValidationErrorType.USER_ERROR_USERNAME_IS_EMPTY));
			}
		}
		return errors;
	}
	
	private boolean isStringsEmpty(String existing, String update) {
		return StringUtils.isEmpty(existing) && 
			 StringUtils.isEmpty(update);
	}

}
