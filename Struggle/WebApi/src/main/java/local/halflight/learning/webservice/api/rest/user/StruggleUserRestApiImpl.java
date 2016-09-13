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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;

import local.halflight.learning.dto.user.StruggleUser;
import local.halflight.learning.dto.user.StruggleUserRequest;
import local.halflight.learning.dto.user.StruggleUserResponse;
import local.halflight.learning.dto.user.StruggleUsersResponse;
import local.halflight.learning.dto.validationerror.ValidationError;
import local.halflight.learning.dto.validationerror.ValidationErrorLevel;
import local.halflight.learning.dto.validationerror.ValidationErrorType;
import local.halflight.learning.validation.HasValidator;
import local.halflight.learning.validation.Validator;
import local.halflight.learning.validation.aspect.UseValidator;
import local.halflight.learning.webservice.api.rest.BaseRestApi;
import local.halflight.learning.webservice.service.StruggleUserService;
import local.halflight.learning.webservice.validation.StruggleUserValidator;

@Component
@Path("/api/users")
@Consumes(MediaType.APPLICATION_XML)
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class StruggleUserRestApiImpl extends BaseRestApi<StruggleUserRequest, StruggleUserResponse>
		implements StruggleUserRestApi, HasValidator {

	private static final Logger LOG = LoggerFactory.getLogger(StruggleUserRestApi.class);
	private static final String DESCRIPTION = "This is REST api for managing StruggleUser... ";

	private StruggleUserService struggleUserService;

	// TODO note at this level we work with request/response abstraction level
	@GET
	@Path("/description")
	@Produces(MediaType.TEXT_PLAIN)
	public String getText() {
		LOG.info("Plain get method returning: {}", DESCRIPTION);
		return DESCRIPTION;
	}

	@GET
	public Response getStruggleUsersList() {
		// TODO add pagination @DefaultValue("10") @QueryParam("listSize")
		// Integer size
		List<StruggleUser> users = struggleUserService.getAllUsers();
		LOG.info("Get method returning XML:{}", users);
		return createResponseWith(Status.OK, new StruggleUsersResponse(users));
	}

	@GET
	@Path("/user")
	public Response getStruggleUserByName(@QueryParam("username") String username) {
		StruggleUser struggleUser = struggleUserService.getUserByName(username).orElseThrow(NotFoundException::new);
		LOG.info("Get method return XML:{}", struggleUser);
		return createResponse(Status.OK, new StruggleUserResponse(struggleUser));
	}

	@GET
	@Path("/user/{username}")
	public Response getStruggleUser(@PathParam("username") String username) {
		StruggleUser struggleUser = struggleUserService.getUserByName(username).orElseThrow(NotFoundException::new);
		LOG.info("Get method return XML:{}", struggleUser);
		return createResponse(Status.OK, new StruggleUserResponse(struggleUser));
	}

	@GET
	@Path("/userinfo")
	public Response getUserInfo(@AuthenticationPrincipal StruggleUser currentUser) {
		LOG.debug("Current user: {}", currentUser);
		return createResponse(Status.OK);
	}

	@POST
	@Path("/user")
	@UseValidator
	@Consumes(MediaType.APPLICATION_XML)
	public Response createStruggleUser(StruggleUserRequest request, @Context UriInfo uri) {
		StruggleUserResponse rp = new StruggleUserResponse();

		try {
			StruggleUser savedUser = struggleUserService.create(request.getPayload());
			rp.setPayload(savedUser);
			return createResponse(Status.CREATED, rp, uri);
		} catch (ValidationException e) {
			return createResponse(Status.INTERNAL_SERVER_ERROR);
		} catch (DuplicateKeyException e) {
			// Move this to service... keyException is on other astraction level
			rp.addValidationError(ValidationErrorLevel.ERROR, ValidationErrorType.USER_ERROR_NAME_ALREADY_EXIST);
			return createResponse(Status.BAD_REQUEST, rp);
		}
	}

	@PUT
	@Path("/user/{userId}")
	@UseValidator
	@Consumes(MediaType.APPLICATION_XML)
	public Response updateStruggleUser(@PathParam("userId") String userId, StruggleUserRequest request) {
		StruggleUser updatedPayload = request.getPayload();
		LOG.info("Request {} for user update received.", updatedPayload);
		StruggleUserResponse rp = new StruggleUserResponse();
		StruggleUser existing = struggleUserService.getUserByName(userId).orElseThrow(NotFoundException::new);
		Map<ValidationErrorLevel, ValidationError> errors = validateUpdate(existing, updatedPayload);
		if (errors.isEmpty()) {
			StruggleUser user = struggleUserService.update(updatedPayload);
			rp.setPayload(user);
			return createResponse(Status.OK, rp);
		} else {
			rp.setValidationErrors(errors);
			rp.setPayload(updatedPayload);
			return createResponse(Status.BAD_REQUEST, rp);
		}

	}

	@DELETE
	@Path("/user/{userId}")
	public Response deleteStruggleUser(@PathParam("userId") String userId) {
		struggleUserService.remove(userId);
		return createResponse(Status.OK);
	}

	private Map<ValidationErrorLevel, ValidationError> validateUpdate(StruggleUser existing, StruggleUser update) {
		StruggleUserValidator v = (StruggleUserValidator) validator;
		return v.validateUpdate(existing, update);
	}

	@Autowired
	public void setStruggleUserService(StruggleUserService struggleUserService) {
		this.struggleUserService = struggleUserService;
	}

	@Autowired
	@Override
	public void setValidator(Validator<StruggleUserRequest> validator) {
		this.validator = validator;
	}

	@Override
	public Validator<StruggleUserRequest> getValidator() {
		return validator;
	}
}
