package com.tati.backend.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConf {
    
    @Bean
    public NewTopic appTopic(){  
        return TopicBuilder.name("weather-data").build();
    } 
}
