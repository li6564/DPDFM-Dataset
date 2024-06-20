package cn.meteor.beyondclouds.config;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 *
 * @author meteor
 */
@org.springframework.context.annotation.Configuration
public class KafkaInitialConfig {
    private cn.meteor.beyondclouds.config.properties.BeyondCloudsKafkaTopicProperties topicProperties;

    @org.springframework.beans.factory.annotation.Autowired
    public KafkaInitialConfig(cn.meteor.beyondclouds.config.properties.BeyondCloudsKafkaTopicProperties topicProperties) {
        this.topicProperties = topicProperties;
    }

    /**
     * 创建DataItemChangeTopic topic
     *
     * @return  */
    @org.springframework.context.annotation.Bean
    public org.apache.kafka.clients.admin.NewTopic initialDataItemChangeTopic() {
        return new org.apache.kafka.clients.admin.NewTopic(topicProperties.getDataItemChange(), 16, ((short) (1)));
    }

    /**
     * 创建UserAction topic
     *
     * @return  */
    @org.springframework.context.annotation.Bean
    public org.apache.kafka.clients.admin.NewTopic initialUserActionTopic() {
        return new org.apache.kafka.clients.admin.NewTopic(topicProperties.getUserAction(), 16, ((short) (1)));
    }
}