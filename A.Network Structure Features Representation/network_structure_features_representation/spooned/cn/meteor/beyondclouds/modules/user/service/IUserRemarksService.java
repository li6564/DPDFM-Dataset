package cn.meteor.beyondclouds.modules.user.service;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-02-12
 */
public interface IUserRemarksService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.user.entity.UserRemarks> {
    /**
     * 修改备注
     *
     * @param currentUserId
     * @param userId
     * @param remarks
     */
    void alterRemarks(java.lang.String currentUserId, java.lang.String userId, java.lang.String remarks);
}