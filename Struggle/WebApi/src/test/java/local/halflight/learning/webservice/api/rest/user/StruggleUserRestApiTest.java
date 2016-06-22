package local.halflight.learning.webservice.api.rest.user;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import javax.security.auth.login.AccountNotFoundException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.assertj.core.api.SoftAssertions;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import local.halflight.learning.dto.struggleuser.StruggleUser;
import local.halflight.learning.dto.struggleuser.StruggleUserConverter;
import local.halflight.learning.dto.struggleuser.StruggleUserRequest;
import local.halflight.learning.dto.struggleuser.StruggleUserResponse;
import local.halflight.learning.dto.struggleuser.UsersResponse;
import local.halflight.learning.testutils.TestDataSource;
import local.halflight.learning.webservice.api.rest.simpletask.SimpleRestApiTest;
import local.halflight.learning.webservice.service.StruggleUserService;
import local.halflight.learning.webservice.validation.StruggleUserValidator;

public class StruggleUserRestApiTest extends BaseRestApiTest {
	private static final Logger LOG = LoggerFactory.getLogger(SimpleRestApiTest.class);
	private static final String TEST_USER_NAME = "TestUserName";
	private static final String TEST_UPDATED_USER_NAME = "UpdatedUserName";

	private StruggleUserRestApiImpl api;
	private final IMocksControl control = EasyMock.createControl();
	private final StruggleUserService service = control.createMock(StruggleUserService.class);
	private final StruggleUserValidator validator = control.createMock(StruggleUserValidator.class);
	private final UriInfo uri = control.createMock(UriInfo.class);
	
	private StruggleUser user;
	private StruggleUserRequest request;
	private URI requestPathURI;
	
	@Before
	public void before() throws URISyntaxException {
		api = new StruggleUserRestApiImpl();
		api.setStruggleUserService(service);
		api.setValidator(validator);
		ReflectionTestUtils.setField(api, "uri", uri);
	
		user =  StruggleUserConverter.toDto(TestDataSource.User.generateUser(TEST_USER_NAME));
		request = new StruggleUserRequest();
		request.setPayload(user);
		requestPathURI = new URI("http://localhost/absolutePathToApi");

	}
	
	@Test
	public void shouldReturnUsers() {
		expect(service.getAllUsers()).andReturn(new ArrayList<>());
		
		control.replay();
		Response rp = api.getStruggleUsers();
		control.verify();
		
		SoftAssertions responseAssertions = new SoftAssertions(); 
		responseAssertions.assertThat(rp).isNotNull();
		responseAssertions.assertThat(rp.getEntity()).isInstanceOf(UsersResponse.class);
	
		responseAssertions.assertAll();
	}
	
	@Test
	public void shouldFindUserByName() {
		expect(service.getUser(TEST_USER_NAME)).andReturn(Optional.of(user));
		
		control.replay();
		Response rp = api.getStruggleUser(TEST_USER_NAME);
		control.verify();
		
		SoftAssertions responseAssertions = new SoftAssertions(); 
		responseAssertions.assertThat(rp).isNotNull();
		
		responseAssertions.assertAll();
	}

	
	@Test
	public void shouldCreateUser() {
		expect(service.create(request.getPayload())).andReturn(user);
		expect(uri.getAbsolutePath()).andReturn(requestPathURI);

		control.replay();
		Response rp = api.createStruggleUser(request);
		control.verify();
		
		StruggleUserResponse serviceRp = (StruggleUserResponse) rp.getEntity();
		
		SoftAssertions responseAssertions = new SoftAssertions(); 
		responseAssertions.assertThat(rp).isNotNull();
		responseAssertions.assertThat(rp.getStatusInfo()).as("RP status").isEqualTo(Status.CREATED);
		LOG.info(rp.getHeaderString("Location"));
		responseAssertions.assertThat(rp.getHeaderString("Location")).as("Created response Location: header").isNotEmpty();
	
		responseAssertions.assertThat(serviceRp.getValidationErrors()).as("ValidationErrors").isNullOrEmpty();
				
		responseAssertions.assertAll();
	
	}
	
	@Test
	public void updateShouldReturnOkIfUpdate() {
		
		StruggleUser existing = StruggleUserConverter.toDto(TestDataSource.User.generateUser(TEST_USER_NAME));
		existing.setUserUUID(UUID.randomUUID().toString());
		
		expect(service.getUser(TEST_USER_NAME)).andReturn(Optional.of(existing));
		expect(validator.validateUpdate(existing, user)).andReturn(Collections.emptyMap());
		expect(service.update(user)).andReturn(user);

		
		control.replay();
		Response rp = api.updateStruggleUser(TEST_USER_NAME, request);
		control.verify();
		
		StruggleUserResponse serviceRp = (StruggleUserResponse) rp.getEntity();
		
		SoftAssertions responseAssertions = new SoftAssertions(); 
		responseAssertions.assertThat(rp).isNotNull();
		responseAssertions.assertThat(rp.getStatusInfo()).as("RP status").isEqualTo(Status.OK);
		responseAssertions.assertThat(serviceRp.getValidationErrors()).as("ValidationErrors").isNullOrEmpty();
				
		responseAssertions.assertAll();
	}

	
	@Test
	public void updateShouldReturnNotFoundIfUserNotExists() {

		expect(service.getUser(TEST_USER_NAME)).andReturn(Optional.empty());
		
		
		control.replay();
		Response rp = api.updateStruggleUser(TEST_USER_NAME, request);
		control.verify();
				
		SoftAssertions responseAssertions = new SoftAssertions(); 
		responseAssertions.assertThat(rp).isNotNull();
		responseAssertions.assertThat(rp.getStatusInfo()).as("RP status").isEqualTo(Status.NOT_FOUND);
				
		responseAssertions.assertAll();
	}
	
	@Test
	public void deleteShouldReturnOkIfSuccesfull()  {

		service.remove(TEST_USER_NAME);

		
		control.replay();
		Response rp = api.deleteStruggleUser(TEST_USER_NAME);
		control.verify();
		
		SoftAssertions responseAssertions = new SoftAssertions(); 
		responseAssertions.assertThat(rp).isNotNull();
		responseAssertions.assertThat(rp.getStatusInfo()).as("RP status").isEqualTo(Status.OK);
				
		responseAssertions.assertAll();	
	}
	
	@Test
	public void deleteShouldReturnNotFoundIfUserNotExists() {

		service.remove(TEST_USER_NAME);
		expectLastCall().andThrow(new NotFoundException());
		
		control.replay();
		Response rp = api.deleteStruggleUser(TEST_USER_NAME);
		control.verify();
				
		SoftAssertions responseAssertions = new SoftAssertions(); 
		responseAssertions.assertThat(rp).isNotNull();
		responseAssertions.assertThat(rp.getStatusInfo()).as("RP status").isEqualTo(Status.NOT_FOUND);
				
		responseAssertions.assertAll();
	}

}
