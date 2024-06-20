package cn.meteor.beyondclouds.modules.queue.service.impl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
/**
 *
 * @author meteor
 */
@org.springframework.stereotype.Service
@lombok.extern.slf4j.Slf4j
public class MessageQueueServiceImpl implements cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService {
    private org.springframework.kafka.core.KafkaTemplate<java.lang.String, java.lang.String> kafkaTemplate;

    private cn.meteor.beyondclouds.config.properties.BeyondCloudsKafkaTopicProperties topicProperties;

    @org.springframework.beans.factory.annotation.Autowired
    public MessageQueueServiceImpl(org.springframework.kafka.core.KafkaTemplate<java.lang.String, java.lang.String> kafkaTemplate, cn.meteor.beyondclouds.config.properties.BeyondCloudsKafkaTopicProperties topicProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicProperties = topicProperties;
    }

    @java.lang.Override
    public void sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage itemUpdateMessage) {
        try {
            kafkaTemplate.send(topicProperties.getDataItemChange(), cn.meteor.beyondclouds.util.JsonUtils.toJson(itemUpdateMessage));
            log.debug("发送kafka消息：{}", itemUpdateMessage.toString());
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            log.error("Kafka消息发送失败：{}", e.getMessage());
        }
    }

    @java.lang.Override
    public void sendUserActionMessage(cn.meteor.beyondclouds.core.queue.message.UserActionMessage userActionMessage) {
        try {
            kafkaTemplate.send(topicProperties.getUserAction(), cn.meteor.beyondclouds.util.JsonUtils.toJson(userActionMessage));
            log.debug("发送kafka消息：{}", userActionMessage.toString());
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            log.error("Kafka消息发送失败：{}", e.getMessage());
        }
    }
}