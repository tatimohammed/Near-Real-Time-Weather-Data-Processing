package com.tati.backend.model;

import java.util.Date;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class Weather {

    @PrimaryKeyColumn(name = "lastupdate", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Date lastUpdate;
    @PrimaryKeyColumn(name = "name", type = PrimaryKeyType.PARTITIONED)
    private String name;

    private String country;
    private String sunrise;
    private String sunset;
    private String temperature;
    private String feelsLike;
    private String humidity;
    private String pressure;
    private String speed;
    private String directionName;
    private String speedName;
    private String weatherIcon;
    private String weatherValue;
    

    public Weather() {
        
        country = "None";
        name = "None";
        sunrise = "None";
        sunset = "None";
        temperature = "None";
        feelsLike = "None";
        humidity = "None";
        pressure = "None";
        speed = "None";
        directionName = "None";
        speedName = "None";
        weatherIcon = "None";
        weatherValue = "None";
        lastUpdate = null;

    }

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public String getSpeed() {
        return speed;
    }

    public String getSpeedName() {
        return speedName;
    }

    public String getDirectionName() {
        return directionName;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public String getWeatherValue() {
        return weatherValue;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public void setDirectionName(String directionName) {
        this.directionName = directionName;
    }

    public void setSpeedName(String speedName) {
        this.speedName = speedName;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public void setWeatherValue(String weatherValue) {
        this.weatherValue = weatherValue;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
