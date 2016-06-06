package local.halflight.learning.webservice.api.rest;

import java.net.URI;
import java.util.Map;

import javax.validation.ValidationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import local.halflight.learning.dto.GenericRequest;
import local.halflight.learning.dto.GenericResponse;
import local.halflight.learning.dto.Payload;
import local.halflight.learning.dto.validationerror.ValidationError;
import local.halflight.learning.dto.validationerror.ValidationErrorLevel;
import local.halflight.learning.webservice.validation.Validator;

public abstract class BaseRestApi<RQ extends GenericRequest<? extends Payload>,
                                  RP extends GenericResponse<? extends GenericRequest<? extends Payload>>> {
	
	private static final Long EMPTY_PAYLOAD_ID = 0l;
	
	@Context
	protected UriInfo uri;
	
	protected Validator<RQ> validator;
	
	public void setValidator(Validator<RQ> validator) {
		this.validator = validator;
	}
	
	
	protected abstract RP handle(RQ request);
	// TODO refactor... think about use aop to validate....
	protected Response handleSync(RQ request) {
		try {
			Map<ValidationErrorLevel, ValidationError>  errors = validator.validate(request);
			if(errors.isEmpty()) {
				RP response = handle(request);
				return createResponse(Status.OK, response);
			}
			else {
				return createResponse(Status.BAD_REQUEST);
			}
		} catch (ValidationException e) {
			return createResponse(Status.BAD_REQUEST);
		}
	}
	
	protected Response handleASync(RQ request) {
		try {
			Map<ValidationErrorLevel, ValidationError> errors = validator.validate(request);
			if(errors.isEmpty()) {
				return createResponse(Status.OK);
			}
			else {
				return createResponse(Status.BAD_REQUEST);
			}
		} catch (ValidationException e) {
			return createResponse(Status.BAD_REQUEST);
		}
	}

	protected Response createResponseWith(Status status, Object entity) {
		ResponseBuilder builder = Response.status(status);
		builder.entity(entity);
		return builder.build();
	}	
	
	protected Response createResponse(Status status) {
		ResponseBuilder builder = Response.status(status);
		return builder.build();
	}

	protected Response createResponse(Status status, RP response) {
		ResponseBuilder builder = Response.status(status);
		builder.entity(response);
		if(status.equals(Status.CREATED)) {
			builder.location(buildLocation(response.getPayloadId().orElse(EMPTY_PAYLOAD_ID)));
		}
		return builder.build();
	}

	// TODO better change to UUID?
	protected URI buildLocation(Long taskId) {
		String location = uri.getAbsolutePath().toString() + "/" + taskId;
		return URI.create(location);
	}
}
