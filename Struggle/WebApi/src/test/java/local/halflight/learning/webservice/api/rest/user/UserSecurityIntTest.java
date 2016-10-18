package local.halflight.learning.webservice.api.rest.user;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import local.halflight.learning.dto.role.Role;
import local.halflight.learning.webservice.TestConfiguration;
import local.halflight.learning.webservice.config.DevWebApiSecurityConfig;
import local.halflight.learning.webservice.service.RoleService;
import local.halflight.learning.webservice.service.RoleServiceImplForSecurityTest;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes={TestConfiguration.class, DevWebApiSecurityConfig.class})
@TestExecutionListeners(listeners={ServletTestExecutionListener.class,
		DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		WithSecurityContextTestExecutionListener.class
})
public class UserSecurityIntTest {

	private Role addRole;
	
	@Autowired
	private RoleService roleService; 
	
	@Before
	public void before() 
	{		
		addRole = new Role("TestRole", "Test description");
	}

	@After
	public void after() {
		SecurityContextHolder.clearContext();
	}
	
	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	public void addRoleUnauthenticated() {
	    roleService.addRole(addRole);
	}

	@Test(expected = AccessDeniedException.class)
	@WithMockUser
	public void updateRoleWithMockUser(){
		roleService.updateRole(addRole);
	}
	
	@Test
	@WithMockUser(username="AdminUser", roles={"ADMIN","USER"})
	public void updateRoleWithAdminMockUser(){
		roleService.updateRole(addRole);
	}
}
