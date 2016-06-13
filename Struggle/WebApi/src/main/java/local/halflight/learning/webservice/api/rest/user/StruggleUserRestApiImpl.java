package local.halflight.learning.webservice.api.rest.user;

import java.util.List;
import java.util.Map;

import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import local.halflight.learning.dto.struggleuser.StruggleUser;
import local.halflight.learning.dto.struggleuser.StruggleUserRequest;
import local.halflight.learning.dto.struggleuser.StruggleUserResponse;
import local.halflight.learning.dto.struggleuser.UsersResponse;
import local.halflight.learning.dto.validationerror.ValidationError;
import local.halflight.learning.dto.validationerror.ValidationErrorLevel;
import local.halflight.learning.dto.validationerror.ValidationErrorType;
import local.halflight.learning.webservice.api.rest.BaseRestApi;
import local.halflight.learning.webservice.service.StruggleUserService;
import local.halflight.learning.webservice.validation.StruggleUserValidator;

/**
 * Enable securiry for delete and post...
 */

@Component
@Path("/api/users")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class StruggleUserRestApiImpl extends BaseRestApi<StruggleUserRequest, StruggleUserResponse>
		implements StruggleUserRestApi {

	private static final Logger LOG = LoggerFactory.getLogger(StruggleUserRestApi.class);
	private static final String DESCRIPTION = "This is REST api for managing StruggleUser... ";

	private StruggleUserService struggleUserService;

	@GET
	@Path("/description")
	@Produces(MediaType.TEXT_PLAIN)
	public String getText() {
		LOG.info("Plain get method returning: {}", DESCRIPTION);
		return DESCRIPTION;
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getStruggleUsers() {
		List<StruggleUser> users = struggleUserService.getAllUsers();
		LOG.info("Get method returning XML:{}", users);
		return createResponseWith(Status.OK, new UsersResponse(users));
	}

	@GET
	@Path("/user/{username}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getStruggleUser(@PathParam("username") String username) {
		StruggleUser struggleUser;

		try {
			if (username.equals("Test")) {
				struggleUser = struggleUserService.getTestUser(username);
			} else {
				struggleUser = struggleUserService.getUser(username).orElseThrow(NotFoundException::new);
			}
			LOG.info("Get method returning XML:{}", struggleUser);
			return createResponse(Status.OK, new StruggleUserResponse(struggleUser));
		} catch (NotFoundException e) {
			return createResponse(Status.NOT_FOUND);
		}
	}

	@POST
	@Path("/user")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response createStruggleUser(StruggleUserRequest request) {

		try {
			validator.validate(request);
			StruggleUser savedUser = struggleUserService.create(request.getPayload());
			return createResponse(Status.CREATED, new StruggleUserResponse(savedUser));
		} catch (ValidationException e) {
			return createResponse(Status.INTERNAL_SERVER_ERROR);
		}
	}

	@PUT
	@Path("/user/{userId}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response updateStruggleUser(@PathParam("userId") String userId, StruggleUserRequest request) {
		StruggleUser updatedPayload = request.getPayload();
		LOG.info("Request {} for user update received.", updatedPayload);
		StruggleUserResponse rp = new StruggleUserResponse();
		try {
			StruggleUser existing = struggleUserService.getUser(userId).orElseThrow(NotFoundException::new);
			Map<ValidationErrorLevel, ValidationError> errors = validateUpdate(existing, updatedPayload);
			if(errors.isEmpty()) {
				StruggleUser user = struggleUserService.update(updatedPayload);
				rp.setPayload(user);
				return createResponse(Status.OK, rp);
			} else {
				rp.setValidationErrors(errors);
				rp.setPayload(updatedPayload);
				return createResponse(Status.BAD_REQUEST, rp);
			}
			
		} catch (NotFoundException e) {
			rp.addValidationError(ValidationErrorLevel.ERROR, ValidationErrorType.USER_ERROR_USERNAME_NOT_FOUND);
			return createResponse(Status.NOT_FOUND, rp);		}

	}

	@DELETE
	@Path("/user/{userId}")
	public Response deleteStruggleUser(@PathParam("userId") String userId) {
		try {
			struggleUserService.remove(userId);
			return createResponse(Status.OK);
		} catch (NotFoundException e) {
			return createResponse(Status.NOT_FOUND);
		}
	}

	@Autowired
	public void setStruggleUserService(StruggleUserService struggleUserService) {
		this.struggleUserService = struggleUserService;
	}

	@Override
	protected StruggleUserResponse handle(StruggleUserRequest request) {
		// TODO
		return null;
	}

	private Map<ValidationErrorLevel, ValidationError> validateUpdate(StruggleUser existing, StruggleUser update) {
		StruggleUserValidator v = (StruggleUserValidator) validator;
		return v.validateUpdate(existing, update);
	}
}
