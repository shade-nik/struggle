package local.halflight.learning.webservice.api.rest;

import java.net.URI;
import java.util.Map;

import javax.validation.ValidationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.hibernate.cfg.NotYetImplementedException;

import local.halflight.learning.dto.GenericRequest;
import local.halflight.learning.dto.GenericResponse;
import local.halflight.learning.dto.Payload;
import local.halflight.learning.dto.validationerror.ValidationError;
import local.halflight.learning.dto.validationerror.ValidationErrorLevel;
import local.halflight.learning.validation.Validator;

public abstract class BaseRestApi<RQ extends GenericRequest<? extends Payload>,
                                  RP extends GenericResponse<? extends GenericRequest<? extends Payload>>> {
	
	private static final Long EMPTY_PAYLOAD_ID = 0l;
	
	@Context
	protected UriInfo uri;
	
	protected Validator<RQ> validator;
	
	public void setValidator(Validator<RQ> validator) {
		this.validator = validator;
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
		return builder.build();
	}
	
	protected Response createResponse(Status status, RP response, UriInfo uri) {
		ResponseBuilder builder = Response.status(status);
		builder.entity(response);
		if(status.equals(Status.CREATED)) {
			URI locationURI = uri.getAbsolutePath().resolve(response.getPayloadId().orElse(EMPTY_PAYLOAD_ID).toString());
			builder.location(locationURI);
			//builder.location(buildLocationHeader(response.getPayloadId().orElse(EMPTY_PAYLOAD_ID).));
		}
		return builder.build();
	}

	protected URI buildLocationHeader(Long id) {
		String location = uri.getAbsolutePath().toString() + "/" + id;
		return URI.create(location);
	}
}
