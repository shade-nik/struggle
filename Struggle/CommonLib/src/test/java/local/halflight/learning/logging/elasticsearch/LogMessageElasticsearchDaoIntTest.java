package local.halflight.learning.logging.elasticsearch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import org.elasticsearch.search.aggregations.support.format.ValueFormat.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;

import local.halflight.learning.config.ElasticRepositoryConfig;
import local.halflight.learning.config.test.TestHandlerConfiguration;
import local.halflight.learning.dao.springdata.elasticsearch.LogMessagesDao;
import local.halflight.learning.dto.elasticsearch.LogLevel;
import local.halflight.learning.dto.elasticsearch.LogMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ElasticRepositoryConfig.class, TestHandlerConfiguration.class})
@ActiveProfiles("test")
public class LogMessageElasticsearchDaoIntTest {
	private static final Logger LOG = LoggerFactory.getLogger(LogMessageElasticsearchDaoIntTest.class);
	private static final String[] GENERATION_TOKENS = {"Sime", "First", "Second", "Other", "Yet Another"};
	
	@Autowired
	private LogMessagesDao logMessagesDao;
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	private List<LogMessage> logMsgs; 
	
	@Before
	public void before() {
		elasticsearchTemplate.deleteIndex(LogMessage.class);
		elasticsearchTemplate.createIndex(LogMessage.class);
		elasticsearchTemplate.putMapping(LogMessage.class);
		elasticsearchTemplate.refresh(LogMessage.class);
	}
	
	@Test
	public void testEntityMapping()
	{
		Map mapping  = elasticsearchTemplate.getMapping(LogMessage.class);
		LOG.info("Maping: {}", mapping);
	}
	
	@Test
	public void testElasticCRUD () {

		//given
		logMsgs = createLogMessages();
		for(int i = 0; i < logMsgs.size() ; i ++) {
			logMessagesDao.save(logMsgs.get(i));
		}
		//when
		
		//then
		assertThat(logMessagesDao.count()).isEqualTo(logMsgs.size());
	}
	
	private static List<LogMessage> createLogMessages() {
		List<LogMessage> resultList = new ArrayList<>();
		for(String token : GENERATION_TOKENS) {
			LogMessage log = createMessage(token);
			resultList.add(log);
		}
		return resultList;
	}

	private static LogMessage createMessage(String token) {
		LogMessage log = new LogMessage();
		log.setClassName("local.halflight.learning.test.ElasticTest");
		Calendar c = Calendar.getInstance();
		c.clear(Calendar.MILLISECOND);
		log.setDate(Calendar.getInstance());
		log.setId(UUID.randomUUID().toString());
		log.setLogLevel(LogLevel.WARN);
		log.setLogMessage("Test log message with token:" + token);
		log.setThreadName(token+"-thread");
		return log;
	}	
	
}
