package cn.meteor.beyondclouds.modules.user.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
 * @since 2020-01-31
 */
@org.springframework.stereotype.Service
public class UserBlacklistServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.user.mapper.UserBlacklistMapper, cn.meteor.beyondclouds.modules.user.entity.UserBlacklist> implements cn.meteor.beyondclouds.modules.user.service.IUserBlacklistService {
    /**
     * 拉黑
     *
     * @param blackedId
     * 		被拉黑用户的id
     * @param userId
     */
    @java.lang.Override
    public void blacklist(java.lang.String blackedId, java.lang.String userId) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException {
        // 自己不能拉黑自己
        if (userId.equals(blackedId)) {
            throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.CAN_NOT_BLACK_SELF);
        }
        // 查询 是否用户已被拉黑
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.user.entity.UserBlacklist> myBlackListQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        myBlackListQueryWrapper.eq("user_id", userId);
        myBlackListQueryWrapper.eq("blacked_id", blackedId);
        if (null != getOne(myBlackListQueryWrapper)) {
            throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.USER_ALREADY_BLACKED);
        }
        cn.meteor.beyondclouds.modules.user.entity.UserBlacklist userBlacklist = new cn.meteor.beyondclouds.modules.user.entity.UserBlacklist();
        userBlacklist.setBlackedId(blackedId);
        userBlacklist.setUserId(userId);
        save(userBlacklist);
    }

    /**
     * 取消拉黑
     *
     * @param blackedId
     * 		取消被拉黑的用户id
     * @param userId
     */
    @java.lang.Override
    public void deBlack(java.lang.String blackedId, java.lang.String userId) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException {
        // 查询 是否用户已被拉黑
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.user.entity.UserBlacklist> blackListQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        blackListQueryWrapper.eq("user_id", userId);
        blackListQueryWrapper.eq("blacked_id", blackedId);
        if (null == getOne(blackListQueryWrapper)) {
            throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.USER_NOT_BLACKED);
        }
        remove(blackListQueryWrapper);
    }

    /**
     * 查看黑名单
     *
     * @param pageNumber
     * @param pageSize
     * @param userId
     * @return  */
    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.user.entity.UserBlacklist> getMyBlackList(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String userId) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.user.entity.UserBlacklist> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.user.entity.UserBlacklist> blacklistQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        blacklistQueryWrapper.eq("user_id", userId);
        return page(page, blacklistQueryWrapper);
    }
}