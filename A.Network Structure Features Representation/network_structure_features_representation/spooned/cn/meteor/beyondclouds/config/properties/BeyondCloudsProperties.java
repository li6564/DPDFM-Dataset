package cn.meteor.beyondclouds.config.properties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 *
 * @author meteor
 */
@lombok.Data
@org.springframework.boot.context.properties.ConfigurationProperties(prefix = "beyondclouds")
public class BeyondCloudsProperties {
    @lombok.Data
    public static class AuthProperties {
        /**
         * QQ认证配置
         */
        private cn.meteor.beyondclouds.config.properties.BeyondCloudsProperties.QQAuthProperties qq;
    }

    @lombok.Data
    public static class QQAuthProperties {
        private java.lang.String clientId;

        private java.lang.String clientSecret;

        private java.lang.String redirectUri;
    }

    /**
     * debug模式开关
     */
    private java.lang.Boolean debug;

    private java.lang.Boolean globalPreventDuplicate = false;

    /**
     * 认证配置
     */
    private cn.meteor.beyondclouds.config.properties.BeyondCloudsProperties.AuthProperties auth;
}