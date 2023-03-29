package com.camel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
public class CamelApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamelApplication.class, args);
	}

}
