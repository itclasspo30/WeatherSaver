package com.weathersaver.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.weathersaver.beans.WeatherBox;

@Service
public class WeatherForecastService {

    @Autowired
    private AmqpTemplate rabbitTemplate;
    
    

    public List<WeatherBox> getForecast(String cityID) {

        synchronized (WeatherForecastService.class) {
            
            String appID = "6982f55dc0a69c8b8a5c113ddf293277";

            final String url = "http://api.openweathermap.org/data/2.5/forecast?id=" + cityID
                    + "&mode=json&units=metric&APPID=" + appID;
            RestTemplate restTemplate = new RestTemplate();

            long startTime = System.currentTimeMillis();
            JSONObject obj = new JSONObject(restTemplate.getForObject(url, String.class));
            long stopTime = System.currentTimeMillis();

            try {
                Thread.sleep(1000 - (stopTime - startTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            JSONArray jArray = obj.getJSONArray("list");

            String name;
            double temp;
            double pressure;
            int humidity;
            int clouds;
            String timestamp;

            name = obj.getJSONObject("city").getString("name");

            List<WeatherBox> weatherList = new ArrayList<WeatherBox>();

            if (jArray != null) {
                for (int i = 0; i < jArray.length(); i++) {
                    temp = jArray.getJSONObject(i).getJSONObject("main").getDouble("temp");
                    pressure = jArray.getJSONObject(i).getJSONObject("main").getDouble("pressure");
                    humidity = jArray.getJSONObject(i).getJSONObject("main").getInt("humidity");
                    clouds = jArray.getJSONObject(i).getJSONObject("clouds").getInt("all");
                    timestamp = jArray.getJSONObject(i).getString("dt_txt");

                    WeatherBox newBox = new WeatherBox(name, temp, pressure, humidity, clouds, timestamp);
                    weatherList.add(newBox);
                }
            }
            return weatherList;

        }

    }
    
    
    
    private String exchange = "myExchange";
    private String routingkey = "myQueue";

    public void sendToSave(List<WeatherBox> weatherList) {
        rabbitTemplate.convertAndSend(exchange, routingkey, weatherList);
        return;
    }
}
