package com.ignisnw.ignisguard.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
@PropertySource(value = "classpath:config/config.yml")
public class BotConfigFile {

    @Value("${telegram_bot.token}")
    private String token;
    @Value("${telegram_bot.username}")
    private String username;
}
