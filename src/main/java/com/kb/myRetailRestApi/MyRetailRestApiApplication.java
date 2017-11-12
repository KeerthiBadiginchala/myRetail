package com.kb.myRetailRestApi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@MapperScan("com.kb.myRetailRestApi.service")
public class MyRetailRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyRetailRestApiApplication.class, args);
	}
	@Bean
	public RestTemplate geRestTemplate() {
		return new RestTemplate();
	}
}
