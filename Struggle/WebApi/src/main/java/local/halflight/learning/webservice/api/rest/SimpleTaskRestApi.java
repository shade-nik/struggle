package local.halflight.learning.webservice.api.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.NotSupportedException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import local.halflight.learning.dto.simpletask.NonAnnotatedTaskListResponse;
import local.halflight.learning.dto.simpletask.SimpleTask;
import local.halflight.learning.dto.simpletask.SimpleTaskResponse;
import local.halflight.learning.webservice.service.SimpleTaskService;

@Component
@Path("/api/simple")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
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
	public JAXBElement<NonAnnotatedTaskListResponse> getTasks(
			@DefaultValue("10") @QueryParam("listSize") Integer size)
	{
		//TODO add pagination... and search filter capabilities 
		LOG.info("Request for task list received.");
		List<SimpleTask> responses = simpleTaskService.findAll();
		NonAnnotatedTaskListResponse resp = new NonAnnotatedTaskListResponse(responses);
		return new JAXBElement<NonAnnotatedTaskListResponse>(new QName("TaskList"), NonAnnotatedTaskListResponse.class, resp);
	}
	
	@GET
	@Path("/task/{taskId}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getTask(@PathParam("taskId") String taskId)
	{
		LOG.info("Request for task id:{} received.", taskId);

		if(taskId != null) 
		{
			if(taskId.equals("Integer")) {
				LOG.info("Return Integer as response");
				return createResponse(Status.OK, new Integer(678));
			}
			else {
				SimpleTask task = simpleTaskService.findTask(taskId);
				SimpleTaskResponse response = new SimpleTaskResponse(task);
				LOG.info("Return object as response: {}", response);
				return createResponse(Status.OK, response);
				
			}
			
		}
		return createResponse(Status.BAD_REQUEST);
	}

	@POST
	@Path("/task")
	public Response createTask(SimpleTask rq)
	{
		LOG.info("Request {} for task create received.", rq);
		SimpleTask saved = simpleTaskService.save(rq);
		return createResponse(Status.OK, new SimpleTaskResponse(rq));
	}
	
	@PUT
	@Path("/task/{taskId}")
	public Response update(@PathParam("taskId") String taskId, SimpleTask update){
		//TODO implement
		throw new NotSupportedException("Not yet implemented");
	}

	@DELETE
	@Path("/task/{taskId}")
	public Response delete(@PathParam("taskId") String taskId){
		//TODO implement
		throw new NotSupportedException("Not yet implemented");
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
