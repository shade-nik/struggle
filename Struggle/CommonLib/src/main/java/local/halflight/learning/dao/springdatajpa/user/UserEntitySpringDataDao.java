package local.halflight.learning.dao.springdatajpa.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import local.halflight.learning.config.TransactionManagerConfiguration;
import local.halflight.learning.entity.struggleuser.UserEntity;

@Repository("userDao")
public interface UserEntitySpringDataDao extends JpaRepository<UserEntity, Long> {
	
	@Query("select u from UserEntity u where u.username = (:username)")
	UserEntity findByName(@Param("username") String username);

	@Query("select u from UserEntity u where u.userUUID = (:uuid)")
	UserEntity findByUUID(@Param("uuid") String uuid);

}
