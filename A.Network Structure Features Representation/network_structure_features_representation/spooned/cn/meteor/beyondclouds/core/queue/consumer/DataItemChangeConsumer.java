package cn.meteor.beyondclouds.core.queue.consumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
/**
 * 数据更新监听器
 * 监听本系统所有的数据更新 操作：更新操作包括
 *
 * @author meteor
 */
@org.springframework.stereotype.Component
@lombok.extern.slf4j.Slf4j
public class DataItemChangeConsumer implements cn.meteor.beyondclouds.core.queue.consumer.TopicConsumer , org.springframework.context.ApplicationContextAware {
    private java.util.Collection<cn.meteor.beyondclouds.core.listener.DataItemChangeListener> listeners;

    @java.lang.Override
    @org.springframework.kafka.annotation.KafkaListener(topics = "${beyondclouds.kafka.topics.data-item-change}")
    public final void onMessage(org.apache.kafka.clients.consumer.ConsumerRecord<?, java.lang.String> record) {
        java.util.Optional<java.lang.String> kafkaMessage = java.util.Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage;
            try {
                dataItemChangeMessage = cn.meteor.beyondclouds.util.JsonUtils.toBean(kafkaMessage.get(), cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.class);
                log.debug("接收到kafka消息：{}", dataItemChangeMessage.toString());
                // 调用每个监听器对应的消息处理函数
                cn.meteor.beyondclouds.core.queue.message.DataItemChangeType changeType = dataItemChangeMessage.getChangeType();
                listeners.forEach(listener -> {
                    // try-catch，保证每个listener互不影响
                    try {
                        switch (changeType) {
                            case ADD :
                                listener.onDataItemAdd(dataItemChangeMessage);
                                break;
                            case DELETE :
                                listener.onDataItemDelete(dataItemChangeMessage);
                                break;
                            case UPDATE :
                                listener.onDataItemUpdate(dataItemChangeMessage);
                                break;
                            case USER_NICK_UPDATE :
                                listener.onUserNickUpdate(dataItemChangeMessage);
                                break;
                            case USER_AVATAR_UPDATE :
                                listener.onUserAvatarUpdate(dataItemChangeMessage);
                                break;
                        }
                    } catch (java.lang.Exception e) {
                        e.printStackTrace();
                        log.error(e.getMessage());
                    }
                });
            } catch (java.lang.Exception e) {
                e.printStackTrace();
                log.error("DataItemUpdateMessage consume failed:{}", e.getMessage());
            }
        }
    }

    @java.lang.Override
    public void setApplicationContext(org.springframework.context.ApplicationContext ctx) throws org.springframework.beans.BeansException {
        java.util.Map<java.lang.String, cn.meteor.beyondclouds.core.listener.DataItemChangeListener> listenerMap = ctx.getBeansOfType(cn.meteor.beyondclouds.core.listener.DataItemChangeListener.class);
        if (!org.springframework.util.CollectionUtils.isEmpty(listenerMap)) {
            this.listeners = listenerMap.values();
        } else {
            this.listeners = java.util.List.of();
        }
    }
}