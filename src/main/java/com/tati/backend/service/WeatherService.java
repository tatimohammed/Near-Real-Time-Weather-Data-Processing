package com.tati.backend.service;

import java.text.ParseException;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import com.tati.backend.model.Weather;

public interface WeatherService {

    public void saveWeatherData(Dataset<Row> weatherDataFrame) throws ParseException;

    public Weather getByName(String name);
    
}
