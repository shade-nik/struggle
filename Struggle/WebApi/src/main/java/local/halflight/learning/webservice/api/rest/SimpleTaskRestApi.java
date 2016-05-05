package local.halflight.learning.webservice.api.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import local.halflight.learning.webservice.service.SimpleTaskService;

@Component
@Path("/api/simple")
public class SimpleTaskRestApi {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleTaskRestApi.class);
	private static final String DESCRIPTION = "This is simple REST api. Used javax.ws.rs and jersey.";
	
	SimpleTaskService simpleTaskService; 
	
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getDescription()
	{
		LOG.info("Plain get method returning: {}", DESCRIPTION);
		return DESCRIPTION;
	}
	
	@GET
	@Path("/tasks")
	public Response getTasks()
	{
		LOG.info("Request for task list received.");
		List<String> responses = simpleTaskService.getTaskList();
		return createResponse(Status.OK, responses);
	}
	
	@GET
	@Path("/task/{taskId}")
	public Response getTask(@PathParam("taskId") String taskId)
	{
		LOG.info("Request for task id:{} received.", taskId);

		if(taskId != null) 
		{
			if(taskId.equals("Integer")) {
				return createResponse(Status.OK, new Integer(678));
			}
			else {
				String response = simpleTaskService.findString(taskId);
				return createResponse(Status.OK, response);
				
			}
			
		}
		return createResponse(Status.BAD_REQUEST);
	}

	@POST
	@Path("/task")
	public Response createTask(Request  rq)
	{
		LOG.info("Request {} for task create received.", rq);
		return createResponse(Status.OK, rq);
	}
	
	private Response createResponse(Status status) 
	{
		ResponseBuilder builder = Response.status(status);
		return builder.build();
	}
	

	private Response createResponse(Status status, Object response) 
	{
		ResponseBuilder builder = Response.status(status);
		builder.entity(response);

		URI location;
		try {
			location = new URI("http://some.url");
			builder.location(location);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return builder.build();
	}
	
	@Autowired
	public void setSimpleTaskService(SimpleTaskService simpleTaskService) {
		this.simpleTaskService = simpleTaskService;
	}
	
}
