package com.example.democosmosissue;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoCosmosIssueApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoCosmosIssueApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		CreateDocumentAndQuery.main(null);
	}
}
