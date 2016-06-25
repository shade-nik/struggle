package local.halflight.learning.dto.elasticsearch;

import com.google.common.base.MoreObjects;

import local.halflight.learning.dto.validationerror.ValidationError;
import local.halflight.learning.dto.validationerror.ValidationErrorLevel;

public class ValidationMessage {
	
	ValidationErrorLevel errorLevel;
	ValidationError error;
	
	public ValidationErrorLevel getErrorLevel() {
		return errorLevel;
	}
	public void setErrorLevel(ValidationErrorLevel errorLevel) {
		this.errorLevel = errorLevel;
	}
	public ValidationError getError() {
		return error;
	}
	public void setError(ValidationError error) {
		this.error = error;
	}
	
	@Override
	public String toString() {
		//@formatter:off
		return MoreObjects.toStringHelper(this)
				.add("errorLevel", errorLevel)
				.add("error", error)
				.toString();
		//@formatter:off
	}
}
