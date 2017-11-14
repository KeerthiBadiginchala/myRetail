package com.kb.myRetailRestApi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.kb.myRetailRestApi.service.RestfulTemplateClient;


@SpringBootApplication
@MapperScan("com.kb.myRetailRestApi.service")
@EnableMongoRepositories(basePackages = "com.kb.myRetailRestApi.repository")
public class MyRetailRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyRetailRestApiApplication.class, args);
	}
	
	@Bean
	public RestfulTemplateClient geRestfulTemplateclient() {
		return new RestfulTemplateClient();
	}
}
