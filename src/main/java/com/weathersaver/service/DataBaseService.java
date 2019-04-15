package com.weathersaver.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.weathersaver.beans.WeatherBox;

@Service
public class DataBaseService {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public boolean addNew(ArrayList<WeatherBox> newList) {
		
		//getting last item from database
		Map<String, Object> targetMap = jdbcTemplate.queryForMap("SELECT * FROM weather_forecast WHERE id = (SELECT MAX(id) FROM weather_forecast)");
		int sumRes = 0;
		
		if (targetMap != null) {
			int curRes = 0;
			String stringTimestamp = (String)targetMap.get("timestamp");
			Timestamp lastTimestamp = Timestamp.valueOf(stringTimestamp);
			
			for(WeatherBox newBox : newList) {
				Timestamp actualTimestamp = Timestamp.valueOf(newBox.getTimestamp());
				if (lastTimestamp.compareTo(actualTimestamp) >= 0 ) {
					curRes = jdbcTemplate.update("UPDATE weather_forecast SET name = ?, temp = ?, pressure = ?, humidity = ?, clouds = ? WHERE timestamp = ?", 
							       newBox.getName(), newBox.getTemp(), newBox.getPressure(), newBox.getHumidity(), newBox.getClouds(), newBox.getTimestamp());
					sumRes = sumRes + curRes;
				} else {
					curRes = jdbcTemplate.update("INSERT INTO weather_forecast (name, temp, pressure, humidity, clouds, timestamp) VALUES (?, ?, ?, ?, ?, ?)", 
	    		    	           newBox.getName(), newBox.getTemp(), newBox.getPressure(), newBox.getHumidity(), newBox.getClouds(), newBox.getTimestamp());
					sumRes = sumRes + curRes;
				}
			}
		} else {
			int curRes = 0;
            for(WeatherBox newBox : newList) {
	    	    curRes = jdbcTemplate.update("INSERT INTO weather_forecast (name, temp, pressure, humidity, clouds, timestamp) VALUES (?, ?, ?, ?, ?, ?)", 
	    	 	        newBox.getName(), newBox.getTemp(), newBox.getPressure(), newBox.getHumidity(), newBox.getClouds(), newBox.getTimestamp());
	    	    sumRes = sumRes + curRes;
			}  
		}
		return sumRes == newList.size();
    }
}
