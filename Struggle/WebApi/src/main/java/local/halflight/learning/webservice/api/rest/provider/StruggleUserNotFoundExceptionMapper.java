package local.halflight.learning.webservice.api.rest.provider;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class StruggleUserNotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
	public Response toResponse(NotFoundException exception) {
    	Response response = Response
                .status(Status.NOT_FOUND)
                .entity(exception.getCause())
                .build();
    	
    	return response; 
	
    }

}
