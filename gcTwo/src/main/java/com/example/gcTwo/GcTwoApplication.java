package com.example.gcTwo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GcTwoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GcTwoApplication.class, args);
	}

}
