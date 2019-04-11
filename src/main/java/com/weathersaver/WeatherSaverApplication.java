package com.weathersaver;

import java.net.URISyntaxException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class WeatherSaverApplication {

	public static void main(String[] args) throws URISyntaxException {
		SpringApplication.run(WeatherSaverApplication.class, args);
		
	}

}
