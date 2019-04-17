package com.weathersaver.beans;

import java.io.Serializable;

public class WeatherBox implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private double temp;
    private double pressure;
    private int humidity;
    private int clouds;
    private String timestamp;

    public WeatherBox() {

    }

    public WeatherBox(String name, double temp, double pressure, int humidity, int clouds) {
        this.name = name;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.clouds = clouds;
    }

    public WeatherBox(String name, double temp, double pressure, int humidity, int clouds, String timestamp) {
        this.name = name;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.clouds = clouds;
        this.timestamp = timestamp;
    }

    public WeatherBox(int id, String name, double temp, double pressure, int humidity, int clouds, String timestamp) {
        this.id = id;
        this.name = name;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.clouds = clouds;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setNull() {
        this.id = 0;
        this.name = null;
        this.temp = 0.0;
        this.pressure = 0.0;
        this.humidity = 0;
        this.clouds = 0;

    }

    @Override
    public String toString() {
        return "WeatherBox [id=" + id + ", name=" + name + ", temp=" + temp + ", pressure=" + pressure + ", humidity="
                + humidity + ", clouds=" + clouds + ", timestamp=" + timestamp + "]";
    }

}
