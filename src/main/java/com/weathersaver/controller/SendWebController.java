package com.weathersaver.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
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
    String cityIDs;

    private List<List<WeatherBox>> forecastList = new ArrayList<List<WeatherBox>>();
    
/*    
    //Каждые 10 минут обновляет данные
    //Раз в 6 секунд берет по 5 городов
    @Scheduled(fixedDelayString = "600000")
    private void AutoUpdate() {

        List<List<WeatherBox>> newForecastList = new ArrayList<List<WeatherBox>>();

        String[] idArray = cityIDs.split(" ");
        int i = 0;
        int j = 0;
        while (i < idArray.length) {
            if (j < 5) {
                newForecastList.add(forecastService.getForecast(idArray[i]));
                i++;
                j++;
            } else {
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                j = 0;
            }
        }
        System.out.println("New Data is Ready. " + idArray.length + " cities.");
        this.forecastList = newForecastList;
    }
*/    
    

    @RequestMapping(value = { "/takeWeather" }, method = RequestMethod.GET)
    public String showWeatherPage(Model model) {
        model.addAllAttributes(forecastList);
        return "takeWeather";
    }
    
    

    @RequestMapping(value = { "/takeWeather" }, method = RequestMethod.POST)
    public String takeWeather(Model model, @ModelAttribute("forecastList") ArrayList<List<WeatherBox>> forecastList) {

        for (String cityID : cityIDs.split(" ")) {
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
