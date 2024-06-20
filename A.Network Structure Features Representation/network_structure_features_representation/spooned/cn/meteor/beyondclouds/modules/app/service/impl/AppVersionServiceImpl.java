package cn.meteor.beyondclouds.modules.app.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-03-19
 */
@org.springframework.stereotype.Service
public class AppVersionServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.app.mapper.AppVersionMapper, cn.meteor.beyondclouds.modules.app.entity.AppVersion> implements cn.meteor.beyondclouds.modules.app.service.IAppVersionService {
    private cn.meteor.beyondclouds.modules.app.mapper.AppVersionMapper appVersionMapper;

    @org.springframework.beans.factory.annotation.Autowired
    public AppVersionServiceImpl(cn.meteor.beyondclouds.modules.app.mapper.AppVersionMapper appVersionMapper) {
        this.appVersionMapper = appVersionMapper;
    }

    /**
     * 查询最新版本信息
     *
     * @return AppVersion
     */
    @java.lang.Override
    public cn.meteor.beyondclouds.modules.app.entity.AppVersion getLatestVersion() throws cn.meteor.beyondclouds.modules.app.exception.VersionServiceException {
        cn.meteor.beyondclouds.modules.app.entity.AppVersion appVersion = appVersionMapper.getLatestVersion();
        if (null == appVersion) {
            throw new cn.meteor.beyondclouds.modules.app.exception.VersionServiceException(cn.meteor.beyondclouds.modules.app.enums.VersionErrorCode.VERSION_FETCH_FAILURE);
        }
        return appVersion;
    }
}