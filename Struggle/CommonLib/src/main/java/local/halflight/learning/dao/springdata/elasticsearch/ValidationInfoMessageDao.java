package local.halflight.learning.dao.springdata.elasticsearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import local.halflight.learning.dto.elasticsearch.ValidationInfoMessage;

public interface ValidationInfoMessageDao extends ElasticsearchRepository<ValidationInfoMessage, String>{

	Page<ValidationInfoMessage> findByDtoType(String dtoType, Pageable pageable);
	
	@Query("{\"bool\": {\"must\": [{\"match\": {\"messages.errorLevel\": \"?0\"}}]}}")
    Page<ValidationInfoMessage> findByMessageErrorLevelUsingCustomQuery(String errorLevel, Pageable pageable);
}
