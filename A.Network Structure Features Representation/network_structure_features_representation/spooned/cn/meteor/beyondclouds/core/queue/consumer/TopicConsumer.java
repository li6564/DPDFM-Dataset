package cn.meteor.beyondclouds.core.queue.consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
/**
 * Topic 消息监听器
 * 监听Kafka队列里面的消息
 *
 * @author meteor
 */
public interface TopicConsumer {
    /**
     * 订阅的topic有新的消息
     *
     * @param record
     */
    void onMessage(org.apache.kafka.clients.consumer.ConsumerRecord<?, java.lang.String> record);
}