package cn.meteor.beyondclouds.modules.app.api;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 段启岩
 * @since 2020-03-19
 */
@io.swagger.annotations.Api(tags = "app接口")
@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping("/api")
public class AppVersionApi {
    private cn.meteor.beyondclouds.modules.app.service.IAppVersionService appVersionService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setAppVersionService(cn.meteor.beyondclouds.modules.app.service.IAppVersionService appVersionService) {
        this.appVersionService = appVersionService;
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("获取最新版本信息")
    @org.springframework.web.bind.annotation.GetMapping("/app/version/latest")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.modules.app.entity.AppVersion> getLatestVersion() {
        try {
            cn.meteor.beyondclouds.modules.app.entity.AppVersion appVersion = appVersionService.getLatestVersion();
            return cn.meteor.beyondclouds.core.api.Response.success(appVersion);
        } catch (cn.meteor.beyondclouds.modules.app.exception.VersionServiceException e) {
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }
}