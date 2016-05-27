package local.halflight.learning.dao.springdatajpa.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import local.halflight.learning.config.TransactionManagerConfiguration;
import local.halflight.learning.dto.hibernate.struggleuser.ProfileEntity;
import local.halflight.learning.dto.hibernate.struggleuser.SettingEntity;

@Repository("settingEntitySpringDataDao")
@Transactional(value = TransactionManagerConfiguration.JPA_TRANSACTION_MANAGER)
public interface SettingEntitySpringDataDao extends JpaRepository<SettingEntity, Long> {

}
