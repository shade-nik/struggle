package local.halflight.learning.webservice.api.rest.provider;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class LearningExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable t) {
		t.printStackTrace();
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(t.getCause()).build();
	}

}
