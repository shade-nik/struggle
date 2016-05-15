package local.halflight.learning.dto.validationerror;

public enum ValidationErrorType {

	RECEIVED_TASK_NAME_ALREADY_IN_DB(400, "Received task name already in database"),

	VALIDATION_ERROR(400, "Base validation error");

	private String description;
	private int code;

	private ValidationErrorType(int code, String description) {
		this.description = description;
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
