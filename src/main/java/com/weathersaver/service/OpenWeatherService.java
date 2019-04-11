package com.weathersaver.service;


import org.json.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.weathersaver.beans.WeatherBox;


@Service
public class OpenWeatherService {
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	
	public WeatherBox readTheWeather()
	{
		int cityID = 627907;  // ID of Hamyel
		String appID = "6982f55dc0a69c8b8a5c113ddf293277";
	    	
		final String url = "http://api.openweathermap.org/data/2.5/weather?id=" + cityID + "&mode=json&units=metric&APPID=" + appID;
	    RestTemplate restTemplate = new RestTemplate();
	    JSONObject obj = new JSONObject(restTemplate.getForObject(url, String.class));
	    
	    String cityName = obj.getString("name");
	    double temp = obj.getJSONObject("main").getDouble("temp");
	    int pressure = obj.getJSONObject("main").getInt("pressure");
	    int humidity = obj.getJSONObject("main").getInt("humidity");
	    int clouds = obj.getJSONObject("clouds").getInt("all");
	    
	    WeatherBox newBox = new WeatherBox(cityName, temp, pressure, humidity, clouds);
	    
	    //System.out.println("Taken data: " + cityName +" "+ temp +" "+ humidity +" "+ pressure +" "+ clouds);
	    
	    return newBox;
	    
	}
	
		
	    //@Value("")
		private String exchange = "myExchange";
		//@Value("")
		private String routingkey  = "myQueue";
		
		public WeatherBox sendToSave(WeatherBox newBox) {
			rabbitTemplate.convertAndSend(exchange, routingkey, newBox);
		
		return newBox;
	}
	
}
