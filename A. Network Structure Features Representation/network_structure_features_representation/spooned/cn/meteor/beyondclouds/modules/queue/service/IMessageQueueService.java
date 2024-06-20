package cn.meteor.beyondclouds.modules.queue.service;
/**
 *
 * @author meteor
 */
public interface IMessageQueueService {
    /**
     * 发送搜索条目更新消息
     *
     * @param itemUpdateMessage
     */
    void sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage itemUpdateMessage);

    /**
     * 发送用户行为消息
     *
     * @param userActionMessage
     */
    void sendUserActionMessage(cn.meteor.beyondclouds.core.queue.message.UserActionMessage userActionMessage);
}