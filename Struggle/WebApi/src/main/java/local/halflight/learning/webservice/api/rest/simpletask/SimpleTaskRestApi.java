package local.halflight.learning.webservice.api.rest.simpletask;

import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;

import local.halflight.learning.dto.simpletask.NonAnnotatedTaskListResponse;
import local.halflight.learning.dto.simpletask.SimpleTask;
import local.halflight.learning.dto.simpletask.SimpleTaskRequest;

public interface SimpleTaskRestApi {

	 //Use wrapper for test purposes only... yes ugly
	 JAXBElement<NonAnnotatedTaskListResponse> getTasks(Integer size);
	
	 Response createTask(SimpleTaskRequest rq);
	 
	 Response getTask(String taskId) ;
		
	 Response update(String taskId, SimpleTaskRequest update);
	
	 Response delete(String taskId);

}
