package com.greendays.greendays;

import com.greendays.greendays.model.dal.Client;
import com.greendays.greendays.model.dal.Garbage;
import com.greendays.greendays.repository.ClientRepository;
import com.greendays.greendays.repository.GarbageRepository;
import com.greendays.greendays.service.StorageProperties;
import com.greendays.greendays.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.mediatype.hal.HalConfiguration;
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
	CommandLineRunner init(StorageService storageService, ClientRepository clientRepository, GarbageRepository garbageRepository) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
			Client client = new Client();
			client.setClientType("Casnic");
			clientRepository.save(client);
			Garbage garbage = new Garbage();
			garbage.setGarbageCode("11234");
			garbage.setGarbageName("Rezidual");
			garbageRepository.save(garbage);

		};
	}

}
