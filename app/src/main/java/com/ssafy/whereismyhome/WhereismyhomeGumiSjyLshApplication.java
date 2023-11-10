package com.ssafy.whereismyhome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ssafy"})
public class WhereismyhomeGumiSjyLshApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhereismyhomeGumiSjyLshApplication.class, args);
		System.out.println("start");
	}

}
