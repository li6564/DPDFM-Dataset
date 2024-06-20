package cn.meteor.beyondclouds.modules.user.service;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 *
 * @author meteor
 */
public interface IUserAuthLocalService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal> {
    /**
     * 根据账号查询本地认证信息
     *
     * @param account
     * @return  */
    cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal getByAccount(java.lang.String account);

    /**
     * 根据手机号查询本地认证信息
     *
     * @param mobile
     * @return  */
    cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal getByMobile(java.lang.String mobile);
}