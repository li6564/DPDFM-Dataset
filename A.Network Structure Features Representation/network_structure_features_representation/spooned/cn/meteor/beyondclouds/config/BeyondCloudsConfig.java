package cn.meteor.beyondclouds.config;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
@org.springframework.context.annotation.Configuration
@org.springframework.context.annotation.PropertySource("classpath:application.yml")
@org.springframework.boot.context.properties.EnableConfigurationProperties({ cn.meteor.beyondclouds.config.properties.BeyondCloudsProperties.class, cn.meteor.beyondclouds.config.properties.BeyondCloudsKafkaTopicProperties.class })
public class BeyondCloudsConfig {}