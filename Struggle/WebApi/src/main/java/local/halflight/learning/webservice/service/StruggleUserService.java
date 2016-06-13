package local.halflight.learning.webservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import local.halflight.learning.dao.springdatajpa.user.UserEntitySpringDataDao;
import local.halflight.learning.dto.hibernate.struggleuser.UserEntity;
import local.halflight.learning.dto.struggleuser.StruggleUser;
import local.halflight.learning.dto.struggleuser.StruggleUserConverter;
import local.halflight.learning.testutils.TestDataSource;

@Component
public class StruggleUserService {
	private static final Logger LOG = LoggerFactory.getLogger(StruggleUserService.class);

	@Autowired
	@Qualifier("userDao")
	private UserEntitySpringDataDao userEntitySpringDataDao;
	
	public Optional<StruggleUser> getUser(String username)  {
		LOG.info("Getting user by name: {}", username);
		UserEntity entity = userEntitySpringDataDao.findByName(username);
		if(entity == null) {
			return Optional.empty();
		}
		LOG.info("Found user entity: {}", entity);
		
		StruggleUser user = StruggleUserConverter.toDto(entity);
		LOG.info("Converted user: {}", user);
		return Optional.of(user);
	}

	public List<StruggleUser> getAllUsers() {
		List<UserEntity> entities = userEntitySpringDataDao.findAll();
		List<StruggleUser> users = entities.stream().map(StruggleUserConverter::toDto).collect(Collectors.toList());
		return users;
	}

	public StruggleUser create(StruggleUser payload) {
		UserEntity saved = userEntitySpringDataDao.save(StruggleUserConverter.toEntity(payload));
		return StruggleUserConverter.toDto(saved);
	}

	public StruggleUser update(StruggleUser payload) {
		//TODO change to find/update since save in my case can't correctly identify user
		return create(payload);
	}

	public void remove(String username) throws NotFoundException {
		LOG.info("Remove user entity: {}", username);
		UserEntity ent = userEntitySpringDataDao.findByName(username);
		if(ent != null) {
			userEntitySpringDataDao.delete(ent);
		}
		else {
			throw new NotFoundException();
		}
	}

	public StruggleUser getTestUser(String username) {
		return StruggleUserConverter.toDto(TestDataSource.User.generateUser("TestUser"));
	}


}
