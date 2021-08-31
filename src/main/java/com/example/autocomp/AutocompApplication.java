package com.example.autocomp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AutocompApplication {
	private final AutocompService autocompService;

	@Autowired
	public AutocompApplication(AutocompService autocompService) {
		this.autocompService = autocompService;
	}

	@Bean
	ApplicationRunner initApp() {
		return args -> {
			this.autocompService.initPrefixTree();
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(AutocompApplication.class, args);
	}
}
