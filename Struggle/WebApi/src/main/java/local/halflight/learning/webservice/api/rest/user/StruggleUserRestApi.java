package local.halflight.learning.webservice.api.rest.user;

import javax.ws.rs.core.Response;

import org.springframework.security.core.userdetails.User;

import local.halflight.learning.dto.struggleuser.StruggleUserRequest;

public interface StruggleUserRestApi {

	public Response getStruggleUsers();

	public Response getStruggleUser(String username);

	public Response createStruggleUser(StruggleUserRequest request);

	public Response updateStruggleUser(String userId, StruggleUserRequest payload);

	public Response deleteStruggleUser(String userId);

}
