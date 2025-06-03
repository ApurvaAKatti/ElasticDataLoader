package com.example.elastic.data.loader.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.ssl.SSLContextBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.security.KeyStore;

@Configuration
public class ElasticClientConfig {

    @Bean
    public ElasticsearchClient elasticsearchClient() throws Exception {
        // File certFile = new File("C:\\elasticsearch-9.0.1-windows-x86_64\\elasticsearch-9.0.1\\config\\certs\\http_ca.crt");

        // SSLContext sslContext = SSLContextBuilder.create()
        //     .loadTrustMaterial(certFile) // handles PEM cert
        //     .build();

        KeyStore truststore = KeyStore.getInstance("JKS");
        try (FileInputStream is = new FileInputStream("C:\\elasticsearch-9.0.1-windows-x86_64\\elasticsearch-9.0.1\\config\\certs\\elasticsearch-ca.jks")) {
            truststore.load(is, "changeit".toCharArray());
        }

        SSLContext sslContext = SSLContextBuilder.create()
            .loadTrustMaterial(truststore, null)
            .build();

        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
            AuthScope.ANY,
            new UsernamePasswordCredentials("elastic", "rzppzzNiOy6+jy8YOlnj")
        );

        RestClientBuilder builder = RestClient.builder(
            new org.apache.http.HttpHost("localhost", 9200, "https")
        ).setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
            .setSSLContext(sslContext)
            .setDefaultCredentialsProvider(credentialsProvider)
        );

        RestClient restClient = builder.build();
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }
}