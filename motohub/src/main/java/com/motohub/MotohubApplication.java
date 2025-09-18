package com.motohub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.motohub", "com.config", "com.security"})
public class MotohubApplication {

	public static void main(String[] args) {
		SpringApplication.run(MotohubApplication.class, args);
	}

}
