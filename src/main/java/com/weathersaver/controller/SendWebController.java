package com.weathersaver.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.weathersaver.beans.WeatherBox;
import com.weathersaver.service.WeatherForecastService;

@Controller
public class SendWebController {

    @Autowired
    WeatherForecastService forecastService;

    @Value("${City}")
    String CityIDs;

    private List<List<WeatherBox>> forecastList = new ArrayList<List<WeatherBox>>();

    @RequestMapping(value = { "/takeWeather" }, method = RequestMethod.GET)
    public String showWeatherPage(Model model) {
        model.addAllAttributes(forecastList);
        return "takeWeather";
    }

    @RequestMapping(value = { "/takeWeather" }, method = RequestMethod.POST)
    public String takeWeather(Model model, @ModelAttribute("forecastList") ArrayList<List<WeatherBox>> forecastList) {

        for (String cityID : CityIDs.split(" ")) {
            forecastList.add(forecastService.getForecast(cityID));
        }

        model.addAllAttributes(forecastList);
        this.forecastList = forecastList;

        return "takeWeather";
    }

    @RequestMapping(value = { "/saveWeather" }, method = RequestMethod.POST)
    public String saveWeather(Model model, @ModelAttribute("forecastMap") ArrayList<List<WeatherBox>> forecastList) {
        if (this.forecastList != null) {

            for (List<WeatherBox> byOneCityList : this.forecastList) {
                forecastService.sendToSave(byOneCityList);
            }

            model.addAttribute("text", "Message sent to the RabbitMQ Successfully");
            forecastList.clear();
        } else {
            model.addAttribute("text", "Message sending failed");
        }
        return "takeWeather";
    }
}
