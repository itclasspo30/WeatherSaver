package com.weathersaver.controller;

import java.util.ArrayList;
import java.util.List;

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
	
	private List<WeatherBox> newList = new ArrayList<WeatherBox>();
	
	
	
	@RequestMapping(value = { "/takeWeather" }, method = RequestMethod.GET)
    public String showWeatherPage(Model model) {
        model.addAttribute("newList", this.newList);        
        return "takeWeather";
    }
	
	
	
    @RequestMapping(value = {"/takeWeather"}, method = RequestMethod.POST)
    public String takeWeather(Model model, @ModelAttribute("newList") ArrayList<WeatherBox> newList) {
    	this.newList = owService.getForecast();
    	model.addAttribute("newList", this.newList);
    	return "takeWeather";
    }
    
    
    
    @RequestMapping(value = { "/saveWeather" }, method = RequestMethod.POST)
    public String saveWeather(Model model, @ModelAttribute("newList") ArrayList<WeatherBox> newList) {
        if (this.newList != null) {
        	owService.sendToSave(this.newList);
        	model.addAttribute("text", "Message sent to the RabbitMQ Successfully");
        	this.newList.clear();
        }else {
        	model.addAttribute("text", "Message sending failed");
        }
        return "takeWeather";
    }
}
