package cn.meteor.beyondclouds.modules.app.service;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-03-19
 */
public interface IAppVersionService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.app.entity.AppVersion> {
    /**
     * 根据最新版本信息
     *
     * @return AppVersion
     */
    cn.meteor.beyondclouds.modules.app.entity.AppVersion getLatestVersion() throws cn.meteor.beyondclouds.modules.app.exception.VersionServiceException;
}