package com.ishan.RideShare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "com.ishan")
@EnableMongoRepositories(basePackages = "com.ishan.repository")
public class UberRideShareApplication {

	public static void main(String[] args) {
		SpringApplication.run(UberRideShareApplication.class, args);
	}

}
