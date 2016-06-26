package local.halflight.learning.model.handlers;

import local.halflight.learning.dto.simpletask.SimpleTaskResponse;

public class SimpleTaskHandlerResponse implements HandlerResponse<SimpleTaskResponse> {

	private SimpleTaskResponse response;
	private String status;
	
	@Override
	public SimpleTaskResponse getResponse() {
		return response;
	}
	
	public void setResponse(SimpleTaskResponse response) {
		this.response = response;
	}
	
	@Override
	public String getStatus() {
		return status;
	}
	
	@Override
	public void setStatus(String status) {
		this.status = status;
	}
	
}
