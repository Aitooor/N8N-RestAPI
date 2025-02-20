package com.ignisnw.ignisguard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IgnisGuardApplication {

	public static void main(String[] args) {
		SpringApplication.run(IgnisGuardApplication.class, args);
	}
}
