package com.airtribe.news;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsApiApplication.class, args);
		System.out.println("News API Application is running!");
	}

}
