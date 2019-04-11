package com.weathersaver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.weathersaver.beans.WeatherBox;
import com.weathersaver.service.OpenWeatherService;

@Controller
public class SendWebController {

	@Autowired
	OpenWeatherService owService;
	
	private WeatherBox newBox = new WeatherBox();
	
	
	@RequestMapping(value = { "/takeWeather" }, method = RequestMethod.GET)
    public String showWeatherPage(Model model) {
		
        model.addAttribute("newBox", this.newBox);        
        return "takeWeather";
    }
	
	
    @RequestMapping(value = {"/takeWeather"}, method = RequestMethod.POST)
    public String takeWeather(Model model, @ModelAttribute("newBox") WeatherBox newBox) {
    	
    	this.newBox = owService.readTheWeather();
    	model.addAttribute("newBox", this.newBox);
    	return "takeWeather";
    }
    
    
    
    @RequestMapping(value = { "/saveWeather" }, method = RequestMethod.POST)
    public String saveWeather(Model model, @ModelAttribute("newBox") WeatherBox newBox) {
 
    	//System.out.println("NewBox in save method = " + this.newBox.toString());
    	
    	String name = this.newBox.getName(); 
        if (name != null && name.length() > 0 ) {
        	owService.sendToSave(this.newBox);
        	model.addAttribute("text", "Message sent to the RabbitMQ Successfully");
        	this.newBox.setNull();
        }else {
        	model.addAttribute("text", "Message sending failed");
        }
        
        return "takeWeather";
    }
}
