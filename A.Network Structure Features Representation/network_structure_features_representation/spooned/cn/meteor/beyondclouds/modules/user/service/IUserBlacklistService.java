package cn.meteor.beyondclouds.modules.user.service;
import cn.meteor.beyondclouds.modules.user.exception.UserServiceException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-31
 */
public interface IUserBlacklistService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.user.entity.UserBlacklist> {
    /**
     * 拉黑
     *
     * @param blackeId
     * @param userId
     */
    void blacklist(java.lang.String blackeId, java.lang.String userId) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException;

    /**
     * 取消拉黑
     *
     * @param blackedId
     * @param userId
     * @throws UserServiceException
     */
    void deBlack(java.lang.String blackedId, java.lang.String userId) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException;

    /**
     * 查看黑名单
     *
     * @param page
     * @param size
     * @param userId
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.user.entity.UserBlacklist> getMyBlackList(java.lang.Integer page, java.lang.Integer size, java.lang.String userId);
}