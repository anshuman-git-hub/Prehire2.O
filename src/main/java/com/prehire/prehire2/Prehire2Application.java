package com.prehire.prehire2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Prehire2Application {

	public static void main(String[] args) {
		SpringApplication.run(Prehire2Application.class, args);
	}

}

