package local.halflight.learning.webservice.api.rest.simpletask;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import local.halflight.learning.dto.simpletask.NonAnnotatedTaskListResponse;
import local.halflight.learning.dto.simpletask.SimpleTask;
import local.halflight.learning.dto.simpletask.SimpleTaskRequest;
import local.halflight.learning.dto.simpletask.SimpleTaskResponse;
import local.halflight.learning.dto.validationerror.ValidationErrorLevel;
import local.halflight.learning.dto.validationerror.ValidationErrorType;
import local.halflight.learning.webservice.api.rest.BaseRestApi;
import local.halflight.learning.webservice.service.SimpleTaskService;
import local.halflight.learning.webservice.validation.Validator;

@Component
@Path("/api/simple")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class SimpleTaskRestApiImpl extends BaseRestApi<SimpleTaskRequest, SimpleTaskResponse> implements SimpleTaskRestApi {

	static final String DESCRIPTION = "This is simple REST api. Used javax.ws.rs jersey implementation.";

	private static final Logger LOG = LoggerFactory.getLogger(SimpleTaskRestApi.class);

	SimpleTaskService simpleTaskService;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getDescription() {
		LOG.info("Plain get method returning: {}", DESCRIPTION);
		return DESCRIPTION;
	}

	@GET
	@Path("/tasks")
	public JAXBElement<NonAnnotatedTaskListResponse> getTasks(
			@DefaultValue("10") @QueryParam("listSize") Integer size) {
		// TODO add pagination... and search filter capabilities
		LOG.info("Request for task list received.");
		List<SimpleTask> responses = simpleTaskService.findAll(	);
		NonAnnotatedTaskListResponse resp = new NonAnnotatedTaskListResponse(responses);
		return new JAXBElement<NonAnnotatedTaskListResponse>(new QName("TaskList"), NonAnnotatedTaskListResponse.class,
				resp);
	}

	@GET
	@Path("/task/{taskId}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getTask(@PathParam("taskId") String taskId) {
		LOG.info("Request for task id:{} received.", taskId);

		if (taskId != null) {
			if (taskId.equals("Test")) {
				LOG.info("Return entity from spring data dao");
				SimpleTask task = simpleTaskService.jpaDaoFindTask("1");
				return createResponse(Status.OK, new SimpleTaskResponse(task));
			} else {
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
	public Response createTask(SimpleTask rq) {
		LOG.info("Request {} for task create received.", rq);
		SimpleTaskResponse rp = new SimpleTaskResponse();
		SimpleTask saved = simpleTaskService.save(rq);
		if (saved != null) {
			rp.setPayload(saved);
			return createResponse(Status.OK, rp);
		} else {
			//TODO move this functional to separate class (some Validator)
			rp.addValidationError(ValidationErrorLevel.ERROR, ValidationErrorType.RECEIVED_TASK_NAME_ALREADY_IN_DB);
			return createResponse(Status.BAD_REQUEST, rp);
		}		
	}

	@PUT
	@Path("/task/{taskId}")
	public Response update(@PathParam("taskId") String taskId, SimpleTask update) {
		LOG.info("Request {} for task update received.", update);
		SimpleTaskResponse rp = new SimpleTaskResponse();
		SimpleTask updated = simpleTaskService.update(update);
		if (updated != null) {
			rp.setPayload(updated);
			return createResponse(Status.OK, rp);
		} else {
			//TODO move this functional to separate class (some Validator)
			rp.addValidationError(ValidationErrorLevel.ERROR, ValidationErrorType.UPDATE_FAILED);
			return createResponse(Status.BAD_REQUEST, rp);
		}
	}

	@DELETE
	@Path("/task/{taskId}")
	public Response delete(@PathParam("taskId") String taskId) {
		LOG.info("Request for task {} delete received.", taskId);
		simpleTaskService.remove(taskId);
		return createResponse(Status.OK);
	}



	@Autowired
	public void setSimpleTaskService(SimpleTaskService simpleTaskService) {
		this.simpleTaskService = simpleTaskService;
	}

	@Autowired
	@Override
	public void setValidator(Validator<SimpleTaskRequest> validator) {
		this.validator = validator;
	}

	@Override
	protected SimpleTaskResponse handle(SimpleTaskRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
