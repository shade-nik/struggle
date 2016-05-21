package local.halflight.learning.model;

import java.util.concurrent.Future;

//
public interface Task<RP extends TaskResponse> {
	
	public Long getId();
//	public Future<RP> execute();
	
}
