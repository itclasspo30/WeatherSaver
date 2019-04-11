package com.weathersaver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weathersaver.beans.WeatherBox;

@Component
public class AddDataListener {
	
	@Autowired
	DataBaseService dataBaseService;
	
    static final Logger logger = LoggerFactory.getLogger(AddDataListener.class);
 
    @RabbitListener(queues = "myQueue")
    public void process(WeatherBox newBox){
    	System.out.println("Weather is coming: " + newBox.toString());
    	
    	if (newBox.getName() != null && newBox.getName().length() > 0 ) {
    		dataBaseService.addNew(newBox);
        }
    }

}
