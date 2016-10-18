package local.halflight.learning.webservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import local.halflight.learning.dao.springdatajpa.user.RoleEntitySpringDataDao;
import local.halflight.learning.dto.role.Role;

@Component
public class RoleServiceImplForSecurityTest implements RoleService {

	private static final Logger LOG = LoggerFactory.getLogger(RoleServiceImplForSecurityTest.class);
	private RoleEntitySpringDataDao roleDao;

	public Role updateRole(Role updated) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();

		Role result = null;
		LOG.debug("[TestLogging] Updating role... [authn user: {}]", userName);
		return result;
	}

	public Role addRole(Role newRole) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();

		Role result = null;
		LOG.debug("[TestLogging] Adding role... [authn user: {}]", userName);
		return result;
	}
	
	@Override
	public Role getRole(String roleName) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		
		LOG.debug("[TestLogging] Getting role... [authn user: {}]", userName);
		return null;
	}
}
