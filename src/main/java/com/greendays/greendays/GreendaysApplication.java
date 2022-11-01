package com.greendays.greendays;

import com.greendays.greendays.service.StorageProperties;
import com.greendays.greendays.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@EnableScheduling
public class GreendaysApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreendaysApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
			if(!Files.exists(Paths.get("/src/main/resources/zipuri"))){
			Files.createDirectory(Paths.get("/src/main/resources/zipuri"));
			}
		};
	}

}
