package cn.meteor.beyondclouds.modules.user.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
/**
 *
 * @author 段启岩
 * @since 2020-01-18
 */
@org.springframework.stereotype.Service
public class UserFollowServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.user.mapper.UserFollowMapper, cn.meteor.beyondclouds.modules.user.entity.UserFollow> implements cn.meteor.beyondclouds.modules.user.service.IUserFollowService {
    private cn.meteor.beyondclouds.modules.user.service.IUserService userService;

    private cn.meteor.beyondclouds.modules.user.service.IUserBlacklistService userBlacklistService;

    private cn.meteor.beyondclouds.common.helper.IRedisHelper redisHelper;

    private cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService;

    private cn.meteor.beyondclouds.modules.user.service.IUserStatisticsService userStatisticsService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setRedisHelper(cn.meteor.beyondclouds.common.helper.IRedisHelper redisHelper) {
        this.redisHelper = redisHelper;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setUserStatisticsService(cn.meteor.beyondclouds.modules.user.service.IUserStatisticsService userStatisticsService) {
        this.userStatisticsService = userStatisticsService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public UserFollowServiceImpl(cn.meteor.beyondclouds.modules.user.service.IUserBlacklistService userBlacklistService) {
        this.userBlacklistService = userBlacklistService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setMessageQueueService(cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService) {
        this.messageQueueService = messageQueueService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setUserService(cn.meteor.beyondclouds.modules.user.service.IUserService userService) {
        this.userService = userService;
    }

    /**
     * 关注
     *
     * @param followedId
     * 		被关注用户的userId
     * @param followerId
     * 		关注者的userId
     */
    @java.lang.Override
    public void follow(java.lang.String followedId, java.lang.String followerId) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException {
        // 自己不能关注自己
        if (followedId.equals(followerId)) {
            throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.CAN_NOT_FOLLOW_SELF);
        }
        // 查询我是否将对方拉入黑名单
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.user.entity.UserBlacklist> myBlackListQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        myBlackListQueryWrapper.eq("user_id", followerId);
        myBlackListQueryWrapper.eq("blacked_id", followedId);
        if (null != userBlacklistService.getOne(myBlackListQueryWrapper)) {
            throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.USER_ALREADY_BLACKED);
        }
        // 查询对方是否将我拉入黑名单
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.user.entity.UserBlacklist> userBlackListQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        userBlackListQueryWrapper.eq("user_id", followedId);
        userBlackListQueryWrapper.eq("blacked_id", followerId);
        if (null != userBlacklistService.getOne(userBlackListQueryWrapper)) {
            throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.YOU_ALREADY_BLACKED);
        }
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.user.entity.UserFollow> userFollowQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        userFollowQueryWrapper.eq("followed_id", followedId);
        userFollowQueryWrapper.eq("follower_id", followerId);
        cn.meteor.beyondclouds.modules.user.entity.UserFollow userFollow = getOne(userFollowQueryWrapper);
        if ((null != userFollow) && (userFollow.getFollowStatus() == 0)) {
            throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.ALREADY_FOLLOWED);
        }
        boolean update = true;
        if (null == userFollow) {
            update = false;
            userFollow = new cn.meteor.beyondclouds.modules.user.entity.UserFollow();
            userFollow.setFollowerId(followerId);
            userFollow.setFollowedId(followedId);
        }
        userFollow.setFollowStatus(0);
        // 1.查询被关注用户的基本信息
        cn.meteor.beyondclouds.modules.user.entity.User followedUser = userService.getById(followedId);
        if (null == followedUser) {
            throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.FOLLOWED_USER_NOT_EXISTS);
        }
        userFollow.setFollowedNick(followedUser.getNickName());
        userFollow.setFollowedAvatar(followedUser.getUserAvatar());
        // 2.查询我的基本信息
        cn.meteor.beyondclouds.modules.user.entity.User followerUser = userService.getById(followerId);
        if (null == followedUser) {
            throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.FOLLOWER_USER_NOT_EXISTS);
        }
        userFollow.setFollowerNick(followerUser.getNickName());
        userFollow.setFollowerAvatar(followerUser.getUserAvatar());
        if (update) {
            update(userFollow, userFollowQueryWrapper);
        } else {
            save(userFollow);
        }
        // 发送消息
        messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.addMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.USER_FOLLOW, userFollow.getUserFollowId()));
    }

    /**
     * 取消关注
     *
     * @param followedId
     * 		被关注用户的userId
     * @param followerId
     * 		关注者户的userId
     */
    @java.lang.Override
    public void delFollow(java.lang.String followedId, java.lang.String followerId) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException {
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.user.entity.UserFollow> userFollowQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        userFollowQueryWrapper.eq("followed_id", followedId);
        userFollowQueryWrapper.eq("follower_id", followerId);
        userFollowQueryWrapper.eq("follow_status", 0);
        cn.meteor.beyondclouds.modules.user.entity.UserFollow userFollow = getOne(userFollowQueryWrapper);
        if (null == userFollow) {
            throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.NON_FOLLOWED);
        }
        userFollow.setFollowStatus(-1);
        // 1.查找符合条件的关注信息
        // 2.将关注状态设置为-1并保存
        update(userFollow, userFollowQueryWrapper);
        // 发送消息
        messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.deleteMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.USER_FOLLOW, userFollow.getUserFollowId()));
    }

    /**
     * 查询粉丝列表
     *
     * @param pageNumber
     * @param pageSize
     * @param userId
     * 		要查询用户的userId
     * @return  */
    @java.lang.Override
    public cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> getFansPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String userId) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.user.entity.UserFollow> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.user.entity.UserFollow> userFollowQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        userFollowQueryWrapper.eq("followed_id", userId);
        userFollowQueryWrapper.eq("follow_status", 0);
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.user.entity.UserFollow> userFollowPage = page(page, userFollowQueryWrapper);
        java.util.List<cn.meteor.beyondclouds.modules.user.entity.UserFollow> userFollowList = userFollowPage.getRecords();
        java.util.List<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> followUserDTOList;
        if (!org.springframework.util.CollectionUtils.isEmpty(userFollowList)) {
            // 取出我的粉丝的ID
            java.util.List<java.lang.String> fanUserIds = userFollowList.stream().map(cn.meteor.beyondclouds.modules.user.entity.UserFollow::getFollowerId).collect(java.util.stream.Collectors.toList());
            // 查询我的粉丝的统计信息
            java.util.List<cn.meteor.beyondclouds.modules.user.entity.UserStatistics> userStatisticsList = userStatisticsService.listByIds(fanUserIds);
            // 将统计信息列表转换成HashMap
            java.util.Map<java.lang.String, cn.meteor.beyondclouds.modules.user.entity.UserStatistics> userStatisticsMap = userStatisticsList.stream().collect(java.util.stream.Collectors.toMap(cn.meteor.beyondclouds.modules.user.entity.UserStatistics::getUserId, userStatistics -> userStatistics));
            // 通过userFollowList构造userInfoWithStatisticsDTOList
            followUserDTOList = userFollowList.stream().map(userFollow -> {
                cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO followUserDTO = new cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO();
                java.lang.String fansId = userFollow.getFollowerId();
                followUserDTO.setUserId(fansId);
                followUserDTO.setUserNick(userFollow.getFollowerNick());
                followUserDTO.setUserAvatar(userFollow.getFollowerAvatar());
                followUserDTO.setStatistics(userStatisticsMap.get(fansId));
                if (cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) {
                    followUserDTO.setFollowedUser(hasFollowedUser(fansId));
                } else {
                    followUserDTO.setFollowedUser(false);
                }
                return followUserDTO;
            }).collect(java.util.stream.Collectors.toList());
        } else {
            followUserDTOList = java.util.List.of();
        }
        // 3.转换分页并返回
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> pageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>();
        cn.meteor.beyondclouds.util.PageUtils.copyMeta(userFollowPage, pageDTO);
        pageDTO.setDataList(followUserDTOList);
        return pageDTO;
    }

    /**
     * 查看当前用户是否关注指定的用户
     *
     * @param userId
     * @return  */
    @java.lang.Override
    public boolean hasFollowedUser(java.lang.String userId) {
        org.springframework.util.Assert.isTrue(cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated(), "subject must be authenticated");
        cn.meteor.beyondclouds.core.authentication.Subject subject = cn.meteor.beyondclouds.util.SubjectUtils.getSubject();
        // 当前用户和要测试的用户ID一样，返回true
        java.lang.String currentUserId = ((java.lang.String) (subject.getId()));
        if (currentUserId.equals(userId)) {
            return true;
        }
        // 从当前用户的关注列表进行判断
        return getCurrentUserFollowedUserIds().contains(userId);
    }

    /**
     * 查询关注列表
     *
     * @param pageNumber
     * @param pageSize
     * @param userId
     * 		要查询用户的userId
     * @return  */
    @java.lang.Override
    public cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> getFollowedPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String userId) {
        // 1.获取所有的关注
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.user.entity.UserFollow> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.user.entity.UserFollow> userFollowQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        userFollowQueryWrapper.eq("follower_id", userId);
        userFollowQueryWrapper.eq("follow_status", 0);
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.user.entity.UserFollow> userFollowPage = page(page, userFollowQueryWrapper);
        java.util.List<cn.meteor.beyondclouds.modules.user.entity.UserFollow> userFollowList = userFollowPage.getRecords();
        // 2.查询关注的用户的统计信息并填充到userInfoWithStatisticsDTOList里面
        java.util.List<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> followUserDTOList;
        if (!org.springframework.util.CollectionUtils.isEmpty(userFollowList)) {
            java.util.List<java.lang.String> followedUserIds = userFollowList.stream().map(cn.meteor.beyondclouds.modules.user.entity.UserFollow::getFollowedId).collect(java.util.stream.Collectors.toList());
            java.util.List<cn.meteor.beyondclouds.modules.user.entity.UserStatistics> userStatisticsList = userStatisticsService.listByIds(followedUserIds);
            java.util.Map<java.lang.String, cn.meteor.beyondclouds.modules.user.entity.UserStatistics> userStatisticsMap = userStatisticsList.stream().collect(java.util.stream.Collectors.toMap(cn.meteor.beyondclouds.modules.user.entity.UserStatistics::getUserId, userStatistics -> userStatistics));
            followUserDTOList = userFollowList.stream().map(userFollow -> {
                cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO followUserDTO = new cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO();
                java.lang.String followedId = userFollow.getFollowedId();
                followUserDTO.setUserId(followedId);
                followUserDTO.setUserNick(userFollow.getFollowedNick());
                followUserDTO.setUserAvatar(userFollow.getFollowedAvatar());
                followUserDTO.setStatistics(userStatisticsMap.get(followedId));
                if (cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) {
                    followUserDTO.setFollowedUser(hasFollowedUser(followedId));
                } else {
                    followUserDTO.setFollowedUser(false);
                }
                return followUserDTO;
            }).collect(java.util.stream.Collectors.toList());
        } else {
            followUserDTOList = java.util.List.of();
        }
        // 3.转换分页并返回
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> pageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>();
        cn.meteor.beyondclouds.util.PageUtils.copyMeta(userFollowPage, pageDTO);
        pageDTO.setDataList(followUserDTOList);
        return pageDTO;
    }

    @java.lang.Override
    public java.util.Set<java.lang.String> getCurrentUserFollowedUserIds() {
        org.springframework.util.Assert.isTrue(cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated(), "subject must be authenticated");
        cn.meteor.beyondclouds.core.authentication.Subject subject = cn.meteor.beyondclouds.util.SubjectUtils.getSubject();
        java.lang.String currentUserId = ((java.lang.String) (subject.getId()));
        return redisHelper.setGet(cn.meteor.beyondclouds.core.redis.RedisKey.USER_FOLLOWED(currentUserId));
    }
}