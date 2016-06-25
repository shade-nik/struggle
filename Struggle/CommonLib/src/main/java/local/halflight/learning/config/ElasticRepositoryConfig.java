package local.halflight.learning.config;

import org.elasticsearch.cluster.ClusterName;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.util.concurrent.EsExecutors;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "local.halflight.learning.dao.springdata.elasticsearch")
public class ElasticRepositoryConfig {

	@Value("${elastic.home}")
	private String pathToElasticHome;
	
	@Bean
	public NodeBuilder nodeBuilder() {
		return new NodeBuilder();
	}
	

	@Bean
	public ElasticsearchTemplate elasticsearchTemplate() {
		
		Settings.Builder elasticSearchSettings = 
				Settings.settingsBuilder()
				.put(ClusterName.SETTING, "learning-cluster")
				.put(EsExecutors.PROCESSORS, 1)
				.put("path.home", "/");

		return new ElasticsearchTemplate(nodeBuilder()
				.local(false)
				.data(false)
				.client(true)
				.settings(elasticSearchSettings)
				.node().client());
	}

}
