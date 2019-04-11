package com.weathersaver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.weathersaver.beans.WeatherBox;

@Service
public class DataBaseService {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public WeatherBox addNew(WeatherBox newBox) {
    	jdbcTemplate.update("INSERT INTO open_weather (name, temp, pressure, humidity, clouds, timestamp) VALUES (?, ?, ?, ?, ?, current_timestamp)", 
    			             newBox.getName(), newBox.getTemp(), newBox.getPressure(), newBox.getHumidity(), newBox.getClouds());
    	return newBox;
    }

}
