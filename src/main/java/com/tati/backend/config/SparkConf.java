package com.tati.backend.config;

import org.apache.spark.sql.SparkSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConf{

    @Value("${spark.app.name}")
    private String appName;

    @Value("${spark.master}")
    private String masterUri;

    @Bean
    public SparkSession sparkSession() {
        return SparkSession.builder()
                .appName(appName)
                .master(masterUri)
                .getOrCreate();
    }

/* 
    @Override
    public void run(String... args) throws Exception {

        // Define the schema for the message format
        StructType schema = DataTypes.createStructType(new StructField[] {
                DataTypes.createStructField("name", DataTypes.StringType, true),
                DataTypes.createStructField("country", DataTypes.StringType, true),
                DataTypes.createStructField("sunrise", DataTypes.StringType, true),
                DataTypes.createStructField("sunset", DataTypes.StringType, true),
                DataTypes.createStructField("temperature", DataTypes.StringType, true),
                DataTypes.createStructField("feelsLike", DataTypes.StringType, true),
                DataTypes.createStructField("humidity", DataTypes.StringType, true),
                DataTypes.createStructField("pressure", DataTypes.StringType, true),
                DataTypes.createStructField("speed", DataTypes.StringType, true),
                DataTypes.createStructField("directionName", DataTypes.StringType, true),
                DataTypes.createStructField("speedName", DataTypes.StringType, true),
                DataTypes.createStructField("weatherIcon", DataTypes.StringType, true),
                DataTypes.createStructField("weatherValue", DataTypes.StringType, true),
                DataTypes.createStructField("lastUpdate", DataTypes.StringType, true)
        });

        // Read data from Kafka and decode it
        Dataset<Row> df = sparkSession().readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", "172.24.0.4:29092")
                .option("subscribe", "weather-data")
                .load();

        // Decode the value column using the defined schema
        Dataset<Row> decoded_df = df.select(
                functions.from_json(functions.col("value").cast("string"), schema).alias("data"));

        StreamingQuery query = decoded_df.writeStream()
                .outputMode("append")
                .format("console")
                .trigger(Trigger.ProcessingTime("10 seconds"))
                .start();

        query.awaitTermination();

    }
*/
}
