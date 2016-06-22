package local.halflight.learning.handlers;

public interface HandlerResponse<RP>  {
	
	RP getResponse();
	
	String getStatus();
	void setStatus(String status);
}

