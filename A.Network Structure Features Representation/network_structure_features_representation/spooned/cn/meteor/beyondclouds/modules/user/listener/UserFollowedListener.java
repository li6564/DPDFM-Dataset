package cn.meteor.beyondclouds.modules.user.listener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
/**
 * 用户登录注销监听器
 *
 * @author meteor
 */
@lombok.extern.slf4j.Slf4j
@org.springframework.stereotype.Component
public class UserFollowedListener implements cn.meteor.beyondclouds.core.listener.DataItemChangeListener {
    private cn.meteor.beyondclouds.modules.user.service.IUserFollowService userFollowService;

    private cn.meteor.beyondclouds.common.helper.IRedisHelper redisHelper;

    @org.springframework.beans.factory.annotation.Autowired
    public UserFollowedListener(cn.meteor.beyondclouds.modules.user.service.IUserFollowService userFollowService, cn.meteor.beyondclouds.common.helper.IRedisHelper redisHelper) {
        this.userFollowService = userFollowService;
        this.redisHelper = redisHelper;
    }

    @java.lang.Override
    public void onDataItemAdd(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) {
        if (!dataItemChangeMessage.getItemType().equals(cn.meteor.beyondclouds.core.queue.message.DataItemType.USER_FOLLOW)) {
            return;
        }
        java.lang.String operatorId = dataItemChangeMessage.getOperatorId();
        java.lang.String itemId = ((java.lang.String) (dataItemChangeMessage.getItemId()));
        cn.meteor.beyondclouds.modules.user.entity.UserFollow userFollow = userFollowService.getById(itemId);
        java.lang.String followerId = userFollow.getFollowerId();
        org.springframework.util.Assert.isTrue(followerId.equals(operatorId), "数据不合法");
        java.lang.String followedId = userFollow.getFollowedId();
        // 用户关注
        if (dataItemChangeMessage.getItemType().equals(cn.meteor.beyondclouds.core.queue.message.DataItemType.USER_FOLLOW)) {
            // 更新关注者的关注列表
            redisHelper.setAdd(cn.meteor.beyondclouds.core.redis.RedisKey.USER_FOLLOWED(operatorId), followedId);
            // 检查被关注者，如果被关注者登录过，则将当前发起关注的用户的ID存入他的粉丝列表里面
            if (redisHelper.hasKey(cn.meteor.beyondclouds.core.redis.RedisKey.USER_FANS(followedId))) {
                redisHelper.setAdd(cn.meteor.beyondclouds.core.redis.RedisKey.USER_FANS(followedId), operatorId);
            }
        }
    }

    @java.lang.Override
    public void onDataItemDelete(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) {
        if (!dataItemChangeMessage.getItemType().equals(cn.meteor.beyondclouds.core.queue.message.DataItemType.USER_FOLLOW)) {
            return;
        }
        java.lang.String operatorId = dataItemChangeMessage.getOperatorId();
        java.lang.String itemId = ((java.lang.String) (dataItemChangeMessage.getItemId()));
        cn.meteor.beyondclouds.modules.user.entity.UserFollow userFollow = userFollowService.getById(itemId);
        java.lang.String followerId = userFollow.getFollowerId();
        org.springframework.util.Assert.isTrue(followerId.equals(operatorId), "数据不合法");
        java.lang.String followedId = userFollow.getFollowedId();
        // 用户关注
        if (dataItemChangeMessage.getItemType().equals(cn.meteor.beyondclouds.core.queue.message.DataItemType.USER_FOLLOW)) {
            // 更新关注者的关注列表
            redisHelper.setRemove(cn.meteor.beyondclouds.core.redis.RedisKey.USER_FOLLOWED(operatorId), followedId);
            // 检查被关注者
            if (redisHelper.hasKey(cn.meteor.beyondclouds.core.redis.RedisKey.USER_FANS(followedId))) {
                redisHelper.setRemove(cn.meteor.beyondclouds.core.redis.RedisKey.USER_FANS(followedId), operatorId);
            }
        }
    }
}