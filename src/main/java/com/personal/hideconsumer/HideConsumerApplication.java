package com.personal.hideconsumer;

import org.reactivecommons.async.impl.config.annotations.EnableDirectAsyncGateway;
import org.reactivecommons.async.impl.config.annotations.EnableMessageListeners;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableMessageListeners
@EnableDirectAsyncGateway
@SpringBootApplication
public class HideConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HideConsumerApplication.class, args);
	}

}
