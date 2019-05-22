package com.weathersaver;

import java.net.URISyntaxException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class WeatherSaverApplication {

	public static void main(String[] args) throws URISyntaxException {
		SpringApplication.run(WeatherSaverApplication.class, args);
		
		StringBuilder sb = new StringBuilder("animals");
		sb.insert(7, "-");
		System.out.println(sb);
		// sb = animals-
		sb.insert(0, "-");
		System.out.println(sb);
		// sb = -animals-
		sb.insert(4, "-");
		// sb = -ani-mals
		System.out.println(sb);
		
		
	}

}
