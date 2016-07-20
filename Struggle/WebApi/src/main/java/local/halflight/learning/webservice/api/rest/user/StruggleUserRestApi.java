package local.halflight.learning.webservice.api.rest.user;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import local.halflight.learning.dto.struggleuser.StruggleUserRequest;

public interface StruggleUserRestApi {

	public Response getStruggleUsers();

	public Response getStruggleUser(String username);

	public Response createStruggleUser(StruggleUserRequest request, UriInfo iri);

	public Response updateStruggleUser(String userId, StruggleUserRequest payload);

	public Response deleteStruggleUser(String userId);

}
