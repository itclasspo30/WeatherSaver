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

    private static final String IS_EXIST_QUERY = "SELECT count(*) FROM weather_forecast WHERE name = ?";

    private static final String SELECT_LAST_QUERY = "SELECT * FROM weather_forecast "
            + "WHERE id = (SELECT MAX(id) FROM weather_forecast WHERE name = ?)";
    private static final String UPDATE_QUERY = "UPDATE weather_forecast "
            + "SET temp = ?, pressure = ?, humidity = ?, clouds = ? WHERE timestamp = ? AND name = ?";
    private static final String INSERT_QUERY = "INSERT INTO weather_forecast "
            + "(name, temp, pressure, humidity, clouds, timestamp) VALUES (?, ?, ?, ?, ?, ?)";
    

    public boolean addNew(ArrayList<WeatherBox> newList) {
        
        int sumRes = 0;

        Map<String, Object> isExistMap = jdbcTemplate.queryForMap(IS_EXIST_QUERY, newList.get(0).getName());
        if ((long) isExistMap.get("count") > 0) {

            int curRes = 0;
            Map<String, Object> targetMap = jdbcTemplate.queryForMap(SELECT_LAST_QUERY, newList.get(0).getName());
            String stringTimestamp = (String) targetMap.get("timestamp");
            Timestamp lastTimestamp = Timestamp.valueOf(stringTimestamp);

            for (WeatherBox newBox : newList) {
                Timestamp actualTimestamp = Timestamp.valueOf(newBox.getTimestamp());
                if (lastTimestamp.compareTo(actualTimestamp) >= 0) {
                    curRes = jdbcTemplate.update(UPDATE_QUERY, newBox.getTemp(), newBox.getPressure(),
                            newBox.getHumidity(), newBox.getClouds(), newBox.getTimestamp(), newBox.getName());
                    sumRes = sumRes + curRes;
                } else {
                    curRes = jdbcTemplate.update(INSERT_QUERY, newBox.getName(), newBox.getTemp(), newBox.getPressure(),
                            newBox.getHumidity(), newBox.getClouds(), newBox.getTimestamp());
                    sumRes = sumRes + curRes;
                }
            }
        } else {
            int curRes = 0;
            for (WeatherBox newBox : newList) {
                curRes = jdbcTemplate.update(INSERT_QUERY, newBox.getName(), newBox.getTemp(), newBox.getPressure(),
                        newBox.getHumidity(), newBox.getClouds(), newBox.getTimestamp());
                sumRes = sumRes + curRes;
            }
        }
        return sumRes == newList.size();
    }
}
