package cn.meteor.beyondclouds.modules.message.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-02-12
 */
@org.springframework.stereotype.Service
public class MessageServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.message.mapper.MessageMapper, cn.meteor.beyondclouds.modules.message.entity.Message> implements cn.meteor.beyondclouds.modules.message.service.IMessageService {
    @java.lang.Override
    public void readMessage(java.lang.String messageId) throws cn.meteor.beyondclouds.core.exception.ServiceException {
        cn.meteor.beyondclouds.modules.message.entity.Message message = getById(messageId);
        if (message == null) {
            throw new cn.meteor.beyondclouds.modules.message.exception.MessageServiceException(cn.meteor.beyondclouds.modules.message.enums.MessageErrorCode.MESSAGE_NOT_EXIST);
        }
        message.setStatus(cn.meteor.beyondclouds.modules.message.enums.MessageStatus.READ.getStatus());
        updateById(message);
    }

    @java.lang.Override
    public void readMessages(java.lang.String userId) {
        com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper updateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper();
        updateWrapper.eq("to_id", userId);
        updateWrapper.set("status", cn.meteor.beyondclouds.modules.message.enums.MessageStatus.READ.getStatus());
        update(updateWrapper);
    }

    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.message.entity.Message> getMessagePage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String userId, java.lang.String type) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.message.entity.Message> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.message.entity.Message> messageQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        messageQueryWrapper.eq("to_id", userId);
        if (null != type) {
            switch (type) {
                case "COMMENT" :
                    messageQueryWrapper.in("msg_type", java.util.List.of(2, 3, 4, 5, 6));
                    break;
                case "PRAISE" :
                    messageQueryWrapper.in("msg_type", java.util.List.of(7, 8, 9, 10));
                    break;
                case "FOLLOW" :
                    messageQueryWrapper.eq("msg_type", 1);
                    break;
                case "NOTICE" :
                    messageQueryWrapper.eq("msg_type", 0);
                    break;
            }
        }
        messageQueryWrapper.orderByDesc("create_time");
        return page(page, messageQueryWrapper);
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.modules.message.entity.Message getMessage(java.lang.String messageId) throws cn.meteor.beyondclouds.modules.message.exception.MessageServiceException {
        // 1. 检测是否存在该消息
        cn.meteor.beyondclouds.modules.message.entity.Message message = getById(messageId);
        if (message == null) {
            throw new cn.meteor.beyondclouds.modules.message.exception.MessageServiceException(cn.meteor.beyondclouds.modules.message.enums.MessageErrorCode.MESSAGE_NOT_EXIST);
        }
        // 2. 返回消息详情
        return message;
    }

    @java.lang.Override
    public void deleteMessage(java.lang.String messageId, java.lang.String userId) throws cn.meteor.beyondclouds.modules.message.exception.MessageServiceException {
        // 1. 判断有没有这条消息
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.message.entity.Message> messageQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        messageQueryWrapper.eq("message_id", messageId).eq("to_id", userId);
        cn.meteor.beyondclouds.modules.message.entity.Message message = getOne(messageQueryWrapper);
        // 2. 若找不到该消息，抛出业务异常
        if (message == null) {
            throw new cn.meteor.beyondclouds.modules.message.exception.MessageServiceException(cn.meteor.beyondclouds.modules.message.enums.MessageErrorCode.MESSAGE_NOT_EXIST);
        }
        // 3. 删除该条消息
        removeById(messageId);
    }

    @java.lang.Override
    public void deleteAllMessage(java.lang.String userId) {
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.message.entity.Message> messageQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        messageQueryWrapper.eq("to_id", userId);
        remove(messageQueryWrapper);
    }

    @java.lang.Override
    public java.lang.Integer getTotalCount(java.lang.String to) {
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.message.entity.Message> messageQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        messageQueryWrapper.eq("to_id", to);
        return count(messageQueryWrapper);
    }

    @java.lang.Override
    public java.lang.Integer getUnReadCount(java.lang.String to) {
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.message.entity.Message> messageQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        messageQueryWrapper.eq("to_id", to);
        messageQueryWrapper.eq("status", 0);
        return count(messageQueryWrapper);
    }
}