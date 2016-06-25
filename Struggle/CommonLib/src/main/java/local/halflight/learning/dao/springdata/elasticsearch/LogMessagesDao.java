package local.halflight.learning.dao.springdata.elasticsearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import local.halflight.learning.dto.elasticsearch.LogMessage;

public interface LogMessagesDao extends ElasticsearchRepository<LogMessage, String>{

	Page<LogMessage> findByClassName(String className, Pageable pageable);
}
