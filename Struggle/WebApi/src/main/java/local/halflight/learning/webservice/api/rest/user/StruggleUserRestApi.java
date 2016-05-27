package local.halflight.learning.webservice.api.rest.user;

import java.net.URI;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.hibernate.cfg.NotYetImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import local.halflight.learning.dto.struggleuser.StruggleUser;
import local.halflight.learning.dto.struggleuser.StruggleUserResponse;
import local.halflight.learning.dto.validationerror.ValidationErrorType;
import local.halflight.learning.webservice.service.StruggleUserService;
import local.halflight.learning.webservice.validation.StruggleUserValidator;

@Component
@Path("/api/users")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class StruggleUserRestApi {

	private static final Logger LOG = LoggerFactory.getLogger(StruggleUserRestApi.class);
	private static final String DESCRIPTION = "This is REST api for managing StruggleUser... ";

	// TODO extract common api components in the super
	// use generics
	@Context
	UriInfo uri;
	private StruggleUserValidator struggleUserValidator;
	private StruggleUserService struggleUserService;

	@GET
	@Path("/description")
	@Produces(MediaType.TEXT_PLAIN)
	public String getText() {
		LOG.info("Plain get method returning: {}", getDescription());
		return getDescription();
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<StruggleUser> getStruggleUsers() {
		List<StruggleUser> users = struggleUserService.getAllUsers();
		LOG.info("Get method returning XML:{}", users);
		return users;
	}

	@GET
	@Path("/user/{username}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getStruggleUser(@PathParam("username") String username) {
		StruggleUser struggleUser = null;

		if (username != null) {
			if (username.equals("Test")) {
				struggleUser = struggleUserService.getTestUser(username);
			} else {
				struggleUser = struggleUserService.getUser(username);
			}
			LOG.info("Get method returning XML:{}", struggleUser);
			return createResponse(Status.CREATED, new StruggleUserResponse(struggleUser));
		}
		LOG.info("Get method returning XML:{}", struggleUser);
		return createResponse(Status.BAD_REQUEST);
	}

	@POST
	@Path("/user")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response createStruggleUser(StruggleUser payload) {

		try {
			struggleUserValidator.validateCreate(payload);
			StruggleUser savedUser = struggleUserService.create(payload);
			return createResponse(Status.CREATED, new StruggleUserResponse(savedUser));
		} catch (AccountNotFoundException e) {
			// TODO Auto-generated catch block
			return createResponse(Status.INTERNAL_SERVER_ERROR);
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			return createResponse(Status.INTERNAL_SERVER_ERROR);
		}
	}

	@PUT
	@Path("/user/{userId}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response updateStruggleUser(@PathParam("userId") String userId, StruggleUser payload) {
		LOG.info("Request {} for user update received.", payload);
		StruggleUserResponse rp = new StruggleUserResponse();
		try {
			struggleUserValidator.validateUpdate(payload);
			StruggleUser user = struggleUserService.update(payload);
			rp.setPayload(user);
			return createResponse(Status.OK, rp);
		} catch (ValidationException vex) {
			rp.addValidationError(ValidationErrorType.VALIDATION_ERROR);
			return createResponse(Status.BAD_REQUEST, rp);
		} catch (AccountNotFoundException e) {
			rp.addValidationError(ValidationErrorType.UPDATE_FAILED);
			return createResponse(Status.BAD_REQUEST, rp);
		} 

	}

	@DELETE
	@Path("/user/{userId}")

	public Response deleteStruggleUser(@PathParam("userId") String userId) {
		try {
			struggleUserService.remove(userId);
			return createResponse(Status.OK);
		} catch (AccountNotFoundException e) {
			return createResponse(Status.BAD_REQUEST);
		}
	}

	public String getDescription() {
		return DESCRIPTION;
	}

	protected Response createResponse(Status status) {
		ResponseBuilder builder = Response.status(status);
		return builder.build();
	}

	protected Response createResponse(Status status, StruggleUserResponse response) {
		ResponseBuilder builder = Response.status(status);
		builder.entity(response);
		if (response.getPayload() != null) {
			builder.location(buildLocation(response.getPayload().getUserUUID()));
		}
		return builder.build();
	}

	protected URI buildLocation(String taskId) {
		String location = uri.getAbsolutePath().toString() + "/" + taskId;
		return URI.create(location);
	}

	@Autowired
	public void setStruggleUserService(StruggleUserService struggleUserService) {
		this.struggleUserService = struggleUserService;
	}

	@Autowired
	public void setStruggleUserValidator(StruggleUserValidator struggleUserValidator) {
		this.struggleUserValidator = struggleUserValidator;
	}
}
