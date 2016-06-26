package local.halflight.learning.dao.springdatajpa.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import local.halflight.learning.config.TransactionManagerConfiguration;
import local.halflight.learning.entity.struggleuser.ProfileEntity;

@Repository("profileEntitySpringDataDao")
@Transactional(value = TransactionManagerConfiguration.JPA_TRANSACTION_MANAGER)
public interface ProfileEntitySpringDataDao extends JpaRepository<ProfileEntity, Long> {

}
