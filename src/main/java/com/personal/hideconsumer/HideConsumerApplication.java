package com.personal.hideconsumer;

import org.reactivecommons.async.impl.config.annotations.EnableMessageListeners;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableMessageListeners
@SpringBootApplication
public class HideConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HideConsumerApplication.class, args);
	}

}
