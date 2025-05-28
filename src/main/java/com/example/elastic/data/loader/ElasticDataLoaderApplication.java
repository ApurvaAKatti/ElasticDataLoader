package com.example.elastic.data.loader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(exclude = { org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration.class })
public class ElasticDataLoaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticDataLoaderApplication.class, args);
	}

}
