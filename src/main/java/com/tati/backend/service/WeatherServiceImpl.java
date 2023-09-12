package com.tati.backend.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tati.backend.model.Weather;
import com.tati.dao.CassandraRepo;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private CassandraRepo weatherRepository;

    @Override
    public void saveWeatherData(Dataset<Row> weatherDataFrame) throws ParseException {
        // Ensure the DataFrame has at least one row
        if (weatherDataFrame.isEmpty()) {
            // Handle the case where there's no data
            return;
        }

        // Get the first (and only) row from the DataFrame
        Row row = weatherDataFrame.first();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Create a Weather object and set its properties
        Weather weatherEntity = new Weather();
        weatherEntity.setCountry(row.getAs("country"));
        weatherEntity.setDirectionName(row.getAs("directionName"));
        weatherEntity.setFeelsLike(row.getAs("feelsLike"));
        weatherEntity.setHumidity(row.getAs("humidity"));
        weatherEntity.setLastUpdate(dateFormat.parse(row.getAs("lastUpdate")));
        weatherEntity.setName(row.getAs("name"));
        weatherEntity.setPressure(row.getAs("pressure"));
        weatherEntity.setSpeed(row.getAs("speed"));
        weatherEntity.setSpeedName(row.getAs("speedName"));
        weatherEntity.setSunrise(row.getAs("sunrise"));
        weatherEntity.setSunset(row.getAs("sunset"));
        weatherEntity.setTemperature(row.getAs("temperature"));
        weatherEntity.setWeatherIcon(row.getAs("weatherIcon"));
        weatherEntity.setWeatherValue(row.getAs("weatherValue"));

        weatherRepository.save(weatherEntity);
    }

    @Override
    public Weather getByName(String name) {
        return weatherRepository.findFirstByNameOrderByLastUpdateDesc(name);
    }

}
