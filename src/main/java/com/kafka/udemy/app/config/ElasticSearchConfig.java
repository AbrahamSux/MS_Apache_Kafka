package com.kafka.udemy.app.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.kafka.udemy.app.constants.ConstantsElasticSearch.*;

@Configuration
public class ElasticSearchConfig {

	@Bean
	public ElasticsearchClient createClient() {

		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(USERNAME, PASSWORD));

		// Create the client
		RestClient restClient = RestClient
				.builder(new HttpHost(HOSTNAME, PORT, SCHEME)).setHttpClientConfigCallback(
						(config) -> config.setDefaultCredentialsProvider(credentialsProvider)
				)
				/*.setDefaultHeaders(new Header[]{
						new BasicHeader("Authorization", "ApiKey " + apiKey)
				})*/
				.build();

		// Create the transport with a Jackson mapper
		ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

		// And create the API client
		return new ElasticsearchClient(transport);
	}

}
