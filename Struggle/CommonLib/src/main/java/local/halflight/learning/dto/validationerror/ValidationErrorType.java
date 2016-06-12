package local.halflight.learning.dto.validationerror;

public enum ValidationErrorType {

	USER_ERROR_USERNAME_IS_EMPTY(400, "User update failed: username is empty."),
	USER_WARNING_UUID_IS_EMPTY(400, "User update waring: userUUID is empty."),
	USER_ERROR_USERNAME_NOT_FOUND(400, "User search failed: user with such username not found."),
	
	RECEIVED_TASK_NAME_ALREADY_IN_DB(400, "Received task name already in database"),
	UPDATE_FAILED(400, "Entity update failed"),

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
