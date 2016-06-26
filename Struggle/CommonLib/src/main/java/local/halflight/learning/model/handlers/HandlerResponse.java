package local.halflight.learning.model.handlers;

public interface HandlerResponse<RP>  {
	
	RP getResponse();
	
	void setResponse(RP optionalRp);
	
	String getStatus();
	
	void setStatus(String status);
}

