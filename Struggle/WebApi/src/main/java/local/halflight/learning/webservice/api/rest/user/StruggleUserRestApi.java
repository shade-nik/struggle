package local.halflight.learning.webservice.api.rest.user;

import java.util.List;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import local.halflight.learning.dto.struggleuser.StruggleUser;
import local.halflight.learning.dto.struggleuser.StruggleUserRequest;

public interface StruggleUserRestApi {

	public Response getStruggleUsers();

	public Response getStruggleUser(String username);

	public Response createStruggleUser(StruggleUserRequest request);

	public Response updateStruggleUser(String userId, StruggleUserRequest payload);

	public Response deleteStruggleUser(String userId);

}
