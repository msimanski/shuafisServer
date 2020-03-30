package com.mfsimanski.shuafisserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.mfsimanski.shuafisserver.storage.StorageProperties;
import com.mfsimanski.shuafisserver.storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ShuafisServerApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(ShuafisServerApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService)
	{
		return (args) ->
		{
			storageService.deleteAll();
			storageService.init();
		};
	}
}
