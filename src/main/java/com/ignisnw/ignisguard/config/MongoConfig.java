package com.ignisnw.ignisguard.config;

import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;

@Configuration
@EnableRetry
public class MongoConfig {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;
    @Value("${spring.data.mongodb.database}")
    private String database;

    @Bean
    @Retryable(
            value = { DataAccessException.class },
            maxAttempts = 5,
            backoff = @Backoff(delay = 2000)
    )
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(MongoClients.create(mongoUri), database);
    }
}