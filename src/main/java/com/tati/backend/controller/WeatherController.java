package com.tati.backend.controller;

import java.text.ParseException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.functions;
import static org.apache.spark.sql.functions.col;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tati.backend.api.WeatherAPI;
import com.tati.backend.model.Weather;
import com.tati.backend.service.WeatherServiceImpl;

@CrossOrigin("*")
@RestController
public class WeatherController {

    private WeatherServiceImpl weatherServiceImpl;

    private SparkSession sparkSession;

    private KafkaTemplate<String, String> kafkaTemplate;

    private String name_api;

    private CountDownLatch kafkaLatch = new CountDownLatch(1);

    @Autowired
    public WeatherController(WeatherServiceImpl weatherServiceImpl, SparkSession sparkSession,
            KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.sparkSession = sparkSession;
        this.weatherServiceImpl = weatherServiceImpl;

    }

    @GetMapping("/weather")
    public ResponseEntity<Weather> producer(@RequestParam(name = "city") String cityName) {
        WeatherAPI api = new WeatherAPI();

        Map<String, String> data = api.getWeatherData(cityName);

        name_api = data.get("name");

        // Kafka Stream
        this.kafkaTemplate.send("weather-data", data.toString());

        try {
            // Wait for the Kafka consumer to complete
            kafkaLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        Weather weather = weatherServiceImpl.getByName(name_api);
        if (weather != null) {
            // Reset the latch for the next request
            kafkaLatch = new CountDownLatch(1);
            return ResponseEntity.ok(weather);
        } else {
            // Reset the latch for the next request
            kafkaLatch = new CountDownLatch(1);
            return ResponseEntity.notFound().build();
        }

    }

    @KafkaListener(topics = "weather-data", groupId = "letsgo")
    public void consumeWeatherData(@Payload String message) throws ParseException {

        // Parse the JSON message and create a Spark DataFrame
        // Dataset<Row> weatherDataFrame = parseAndTransformToDataFrame(message);
        String data = message.replaceAll(", ", "\", \"");
        data = data.replaceAll("=", "\":\"");

        data = data.replaceAll("\\{", "{\"");
        data = data.replaceAll("\\}", "\"}");
        data = data.replaceAll("\"\\{", "{");

        Dataset<Row> weatherDataFrame = parseAndTransformToDataFrame(data);

        // weatherDataFrame.show();
        // Store the data in Cassandra using Spring Cassandra JPA
        weatherServiceImpl.saveWeatherData(weatherDataFrame);

        // Signal that processing is complete
        kafkaLatch.countDown();

    }

    private Dataset<Row> parseAndTransformToDataFrame(String message) {

        // Assuming your Kafka message is in JSON format
        Dataset<Row> weatherData = sparkSession.read()
                .json(sparkSession.createDataset(Collections.singletonList(message), Encoders.STRING()));

        // Define a User-Defined Function (UDF) to extract the time
        sparkSession.udf().register("extractTime", (String datetime) -> datetime.split("T")[1], DataTypes.StringType);

        // Split the sunrise and sunset columns to get the time
        weatherData = weatherData.withColumn("sunrise", functions.callUDF("extractTime", col("sunrise")))
                .withColumn("sunset", functions.callUDF("extractTime", col("sunset")));

        // Define a User-Defined Function (UDF) to replace "T" with a space
        sparkSession.udf().register("replaceTWithSpace", (String datetime) -> datetime.replace("T", " "),
                DataTypes.StringType);

        // Replace "T" with a space in the lastUpdate column
        weatherData = weatherData.withColumn("lastUpdate", functions.callUDF("replaceTWithSpace", col("lastUpdate")));

        return weatherData;
    }

}
