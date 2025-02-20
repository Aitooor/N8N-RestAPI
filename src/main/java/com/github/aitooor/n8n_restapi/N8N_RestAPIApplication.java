package com.github.aitooor.n8n_restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class N8N_RestAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(N8N_RestAPIApplication.class, args);
	}
}
