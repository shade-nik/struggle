package local.halflight.learning.webservice.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

import local.halflight.learning.dto.role.Role;

public interface RoleService {

	@Secured("autenticated")
	public Role addRole(Role newRole);
	
	@Secured({ "ADMIN", "ROLE_ADMIN" })
//	@PreAuthorize("hasRole('ADMIN')")
	public Role updateRole(Role updated);
	
	@PreAuthorize("autenticated")
	public Role getRole(String roleName);
	
}
