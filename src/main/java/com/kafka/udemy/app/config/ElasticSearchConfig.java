package com.kafka.udemy.app.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {

	private static final String HOSTNAME = "localhost";
	private static final int PORT = 9200;
	private static final String SCHEME = "http";

	// BASIC AUTH
	private static final String USERNAME = "elastic";
	private static final String PASSWORD = "AsFgafYUZkVQYv_tj09E";


	@Bean(destroyMethod = "close")
	public RestHighLevelClient createClient() {
		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(USERNAME, PASSWORD));

		return new RestHighLevelClient(
				RestClient.builder(new HttpHost(HOSTNAME, PORT,SCHEME)).setHttpClientConfigCallback(
						(config) -> config.setDefaultCredentialsProvider(credentialsProvider)
				)
		);
	}

}
