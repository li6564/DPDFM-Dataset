package cn.meteor.beyondclouds.config.properties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@org.springframework.boot.context.properties.ConfigurationProperties(prefix = "aliyun")
@lombok.Data
public class AliyunProperties {
    private java.lang.String accessKeyId;

    private java.lang.String accessKeySecret;

    private cn.meteor.beyondclouds.config.properties.OssProperties oss;

    private cn.meteor.beyondclouds.config.properties.SmsProperties sms;
}