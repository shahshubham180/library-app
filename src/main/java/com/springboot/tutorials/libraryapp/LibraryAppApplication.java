package com.springboot.tutorials.libraryapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.springboot.tutorials.libraryapp.customer.utils.CustomerUtils;

@SpringBootApplication
public class LibraryAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryAppApplication.class, args);
	}
	
	@Bean
	public CustomerUtils getCustomerUtils() {
		return new CustomerUtils();
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
