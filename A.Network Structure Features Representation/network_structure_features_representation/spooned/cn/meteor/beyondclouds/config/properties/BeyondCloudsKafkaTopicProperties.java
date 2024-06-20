package cn.meteor.beyondclouds.config.properties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 *
 * @author meteor
 */
@lombok.Data
@org.springframework.boot.context.properties.ConfigurationProperties(prefix = "beyondclouds.kafka.topics")
public class BeyondCloudsKafkaTopicProperties {
    /**
     * 数据条目更新
     */
    private java.lang.String dataItemChange = "topic.beyondclouds.dataitem.change";

    /**
     * 用户相关操作
     */
    private java.lang.String userAction = "topic.beyondclouds.useraction";
}