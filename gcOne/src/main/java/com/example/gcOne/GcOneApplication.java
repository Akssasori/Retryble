package com.example.gcOne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GcOneApplication {

	public static void main(String[] args) {
		SpringApplication.run(GcOneApplication.class, args);
	}

}
