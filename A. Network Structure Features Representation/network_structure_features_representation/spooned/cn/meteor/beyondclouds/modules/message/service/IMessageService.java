package cn.meteor.beyondclouds.modules.message.service;
import cn.meteor.beyondclouds.modules.message.exception.MessageServiceException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-02-12
 */
public interface IMessageService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.message.entity.Message> {
    /**
     * 标记一个信息为已读
     *
     * @param messageId
     */
    void readMessage(java.lang.String messageId) throws cn.meteor.beyondclouds.core.exception.ServiceException;

    /**
     * 将所有消息标记为已读
     *
     * @param userId
     */
    void readMessages(java.lang.String userId);

    /**
     * 获取我的消息列表
     *
     * @param pageNumber
     * @param pageSize
     * @param userId
     * @param type
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.message.entity.Message> getMessagePage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String userId, java.lang.String type);

    /**
     * 获取消息详情
     *
     * @param messageId
     * @return  */
    cn.meteor.beyondclouds.modules.message.entity.Message getMessage(java.lang.String messageId) throws cn.meteor.beyondclouds.modules.message.exception.MessageServiceException;

    /**
     * 删除单个消息
     *
     * @param messageId
     * @param userId
     * @throws MessageServiceException
     */
    void deleteMessage(java.lang.String messageId, java.lang.String userId) throws cn.meteor.beyondclouds.modules.message.exception.MessageServiceException;

    /**
     * 删除所有消息
     *
     * @param userId
     */
    void deleteAllMessage(java.lang.String userId);

    /**
     * 获取消息数量
     *
     * @param to
     * @return  */
    java.lang.Integer getTotalCount(java.lang.String to);

    /**
     * 获取未读数量
     *
     * @param to
     * @return  */
    java.lang.Integer getUnReadCount(java.lang.String to);
}