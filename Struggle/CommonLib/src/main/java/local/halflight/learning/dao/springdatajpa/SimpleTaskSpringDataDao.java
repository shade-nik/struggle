package local.halflight.learning.dao.springdatajpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import local.halflight.learning.config.TransactionManagerConfiguration;
import local.halflight.learning.dto.hibernate.simpletask.SimpleTaskDbEntity;

@Repository("simpleTaskSpringDataDao")
@Transactional(value = TransactionManagerConfiguration.JPA_TRANSACTION_MANAGER)
public interface SimpleTaskSpringDataDao extends JpaRepository<SimpleTaskDbEntity, Long>{
	//IMPORTANT
	//Have to make an explicit call on the lazy collection in order to initialize it
	@Query("select t from SimpleTaskDbEntity t join fetch t.notes where t.id = (:id)")
	SimpleTaskDbEntity findOne(@Param("id") Long id);
	
	@Query("select t from SimpleTaskDbEntity t join fetch t.notes where t.taskName = :taskname")
	List<SimpleTaskDbEntity> findByTaskName(@Param("taskname") String taskname);
}
