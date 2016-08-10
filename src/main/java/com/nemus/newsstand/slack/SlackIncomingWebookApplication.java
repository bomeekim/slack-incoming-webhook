package com.nemus.newsstand.slack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SlackIncomingWebookApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlackIncomingWebookApplication.class, args);
	}
}
