package cn.meteor.beyondclouds.modules.user.service;
import cn.meteor.beyondclouds.modules.user.exception.UserServiceException;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 *
 * @author meteor
 */
public interface IUserService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.user.entity.User> {
    /**
     * 使用手机号注册用户
     *
     * @param mobile
     * @param password
     * @param verifyCode
     * @exception UserServiceException
     */
    void registerByMobile(java.lang.String mobile, java.lang.String password, java.lang.String verifyCode) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException;

    /**
     * 邮箱注册
     *
     * @param email
     * @param password
     * @throws UserServiceException
     */
    void registerByEmail(java.lang.String email, java.lang.String password) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException;

    /**
     * 修改昵称
     *
     * @param userId
     * @param nickName
     */
    void updateNickName(java.lang.String userId, java.lang.String nickName);

    /**
     * 通过QQ注册用户
     *
     * @param qqAuthResult
     * @return  */
    cn.meteor.beyondclouds.modules.user.entity.User qqRegister(cn.meteor.beyondclouds.common.dto.QQAuthResultDTO qqAuthResult);

    /**
     * 修改用户基本信息
     *
     * @param user
     */
    void alterBaseInfo(cn.meteor.beyondclouds.modules.user.entity.User user) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException;

    /**
     * 修改密码
     *
     * @param mobile
     * @param password
     * @param verifyCode
     * @throws UserServiceException
     */
    void alterPassword(java.lang.String mobile, java.lang.String password, java.lang.String verifyCode) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException;

    /**
     * 激活账号
     *
     * @param activeCode
     */
    void activeAccount(java.lang.String activeCode) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException;

    /**
     * 绑定邮箱
     *
     * @param email
     * @param verifyCode
     * @param userId
     */
    void bindEMail(java.lang.String email, java.lang.String verifyCode, java.lang.String userId) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException;

    /**
     * 绑定手机号
     *
     * @param mobile
     * @param verifyCode
     * @param userId
     */
    void bindMobile(java.lang.String mobile, java.lang.String verifyCode, java.lang.String userId) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException;

    /**
     * 获得统计信息
     *
     * @param userId
     * @param updateVisted
     * @return  */
    cn.meteor.beyondclouds.modules.user.entity.UserStatistics getStatistics(java.lang.String userId, boolean updateVisted) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException;

    /**
     * 获取用户信息
     *
     * @param userId
     * @param updateVisited
     * @return  */
    cn.meteor.beyondclouds.modules.user.dto.UserInfoDTO getUserInfo(java.lang.String userId, boolean updateVisited) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException;

    /**
     * 获取热门博主
     *
     * @param pageNumber
     * @param pageSize
     * @return  */
    cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> getHotBloggers(java.lang.Integer pageNumber, java.lang.Integer pageSize);

    /**
     * 批量查询用户-带统计信息
     *
     * @param userIds
     * @return  */
    java.util.List<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> listByIdsWithStatistics(java.util.List<java.lang.String> userIds);

    /**
     * 获取社区活跃用户
     *
     * @param page
     * @param size
     * @return  */
    cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> getActivesUsers(java.lang.Integer page, java.lang.Integer size);

    /**
     * 获取社区精英
     *
     * @param page
     * @param size
     * @return  */
    cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> getEliteUsers(java.lang.Integer page, java.lang.Integer size);

    /**
     * 获取热门答主
     *
     * @param page
     * @param size
     * @return  */
    cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> getHotReplies(java.lang.Integer page, java.lang.Integer size);

    /**
     * 注销登录
     */
    void logout();
}