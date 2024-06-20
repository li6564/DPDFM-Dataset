package cn.meteor.beyondclouds.modules.user.service;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 *
 * @author 段启岩
 * @since 2020-01-18
 */
public interface IUserFollowService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.user.entity.UserFollow> {
    /**
     * 关注
     *
     * @param followedId
     * 		关注者的ID
     * @param followerId
     * 		被关注者的ID
     */
    void follow(java.lang.String followedId, java.lang.String followerId) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException;

    /**
     * 取消关注
     *
     * @param followedId
     * @param followerId
     */
    void delFollow(java.lang.String followedId, java.lang.String followerId) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException;

    /**
     * 根据userId查询粉丝
     *
     * @param pageNumber
     * @param pageSize
     * @param userId
     * @return  */
    cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> getFansPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String userId);

    /**
     * 根据userId查询关注的用户
     *
     * @param pageNumber
     * @param pageSize
     * @param userId
     * @return  */
    cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> getFollowedPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String userId);

    /**
     * 判断当前用户有没有关注目标用户
     *
     * @param userId
     * @return  */
    boolean hasFollowedUser(java.lang.String userId);

    /**
     * 获取当前用户关注的所有用户的ID
     *
     * @return  */
    java.util.Set<java.lang.String> getCurrentUserFollowedUserIds();
}