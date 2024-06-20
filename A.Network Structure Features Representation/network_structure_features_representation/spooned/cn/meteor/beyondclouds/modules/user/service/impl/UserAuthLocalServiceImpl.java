package cn.meteor.beyondclouds.modules.user.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author meteor
 */
@org.springframework.stereotype.Service
public class UserAuthLocalServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.user.mapper.UserAuthLocalMapper, cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal> implements cn.meteor.beyondclouds.modules.user.service.IUserAuthLocalService {
    private cn.meteor.beyondclouds.modules.user.service.IUserAuthLocalService userAuthLocalService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setUserAuthLocalService(cn.meteor.beyondclouds.modules.user.service.IUserAuthLocalService userAuthLocalService) {
        this.userAuthLocalService = userAuthLocalService;
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal getByAccount(java.lang.String account) {
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal> queryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        queryWrapper.eq("account", account);
        return getOne(queryWrapper);
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal getByMobile(java.lang.String mobile) {
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal> queryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        queryWrapper.eq("account", mobile);
        queryWrapper.eq("account_type", cn.meteor.beyondclouds.modules.user.enums.AccountType.MOBILE.getType());
        return getOne(queryWrapper);
    }
}