package cn.meteor.beyondclouds.modules.user.listener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
/**
 * 用户登录注销监听器
 *
 * @author meteor
 */
@lombok.extern.slf4j.Slf4j
@org.springframework.stereotype.Component
public class UserLoginAndLogoutListener implements cn.meteor.beyondclouds.core.listener.UserActionListener {
    private cn.meteor.beyondclouds.modules.user.service.IUserFollowService userFollowService;

    private cn.meteor.beyondclouds.common.helper.IRedisHelper redisHelper;

    @org.springframework.beans.factory.annotation.Autowired
    public UserLoginAndLogoutListener(cn.meteor.beyondclouds.modules.user.service.IUserFollowService userFollowService, cn.meteor.beyondclouds.common.helper.IRedisHelper redisHelper) {
        this.userFollowService = userFollowService;
        this.redisHelper = redisHelper;
    }

    @java.lang.Override
    public void onUserLogin(cn.meteor.beyondclouds.core.queue.message.UserActionMessage userActionMessage) {
        java.lang.String userId = userActionMessage.getUserId();
        // 查找用户关注的所有人
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.user.entity.UserFollow> userFollowQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        userFollowQueryWrapper.eq("follower_id", userId);
        userFollowQueryWrapper.eq("follow_status", 0);
        java.util.List<cn.meteor.beyondclouds.modules.user.entity.UserFollow> userFollowList = userFollowService.list(userFollowQueryWrapper);
        java.util.List<java.lang.String> followedUserIds = userFollowList.stream().map(cn.meteor.beyondclouds.modules.user.entity.UserFollow::getFollowedId).collect(java.util.stream.Collectors.toList());
        // 查找用户的所以粉丝
        userFollowQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        userFollowQueryWrapper.eq("follow_status", 0);
        userFollowList = userFollowService.list(userFollowQueryWrapper);
        java.util.List<java.lang.String> fansIds = userFollowList.stream().map(cn.meteor.beyondclouds.modules.user.entity.UserFollow::getFollowerId).collect(java.util.stream.Collectors.toList());
        // 将用户的关注列表和粉丝列表存入redis
        redisHelper.del(cn.meteor.beyondclouds.core.redis.RedisKey.USER_FOLLOWED(userId));
        redisHelper.del(cn.meteor.beyondclouds.core.redis.RedisKey.USER_FANS(userId));
        if (!org.springframework.util.CollectionUtils.isEmpty(followedUserIds)) {
            redisHelper.setAdd(cn.meteor.beyondclouds.core.redis.RedisKey.USER_FOLLOWED(userId), followedUserIds.toArray(new java.lang.String[0]));
        } else {
            redisHelper.setAdd(cn.meteor.beyondclouds.core.redis.RedisKey.USER_FOLLOWED(userId), "");
        }
        if (!org.springframework.util.CollectionUtils.isEmpty(fansIds)) {
            redisHelper.setAdd(cn.meteor.beyondclouds.core.redis.RedisKey.USER_FANS(userId), "fansIds.toArray(new String[0])");
        } else {
            redisHelper.setAdd(cn.meteor.beyondclouds.core.redis.RedisKey.USER_FANS(userId), "");
        }
        log.debug("已经将用户{}的关注列表和粉丝列表存入redis.", userId);
    }

    @java.lang.Override
    public void onUserLogout(cn.meteor.beyondclouds.core.queue.message.UserActionMessage userActionMessage) {
        java.lang.String userId = userActionMessage.getUserId();
        // 将用户的关注列表和粉丝列表从redis删除
        redisHelper.del(cn.meteor.beyondclouds.core.redis.RedisKey.USER_FOLLOWED(userId));
        redisHelper.del(cn.meteor.beyondclouds.core.redis.RedisKey.USER_FANS(userId));
        log.debug("检测到用户注销事件：{}", userId);
    }
}