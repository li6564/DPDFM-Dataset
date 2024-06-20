package cn.meteor.beyondclouds.config;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
/**
 *
 * @author meteor
 */
@org.springframework.context.annotation.Configuration
@org.springframework.context.annotation.PropertySource("classpath:application.yml")
@org.springframework.boot.context.properties.EnableConfigurationProperties({ cn.meteor.beyondclouds.config.properties.AliyunProperties.class })
public class AliyunConfig {
    private cn.meteor.beyondclouds.config.properties.AliyunProperties aliyunProperties;

    @org.springframework.beans.factory.annotation.Autowired
    public AliyunConfig(cn.meteor.beyondclouds.config.properties.AliyunProperties aliyunProperties) {
        this.aliyunProperties = aliyunProperties;
    }

    @org.springframework.context.annotation.Bean
    public com.aliyun.oss.OSS oss() {
        return new com.aliyun.oss.OSSClientBuilder().build(aliyunProperties.getOss().getEndpoint(), aliyunProperties.getAccessKeyId(), aliyunProperties.getAccessKeySecret());
    }

    @org.springframework.context.annotation.Bean
    public com.aliyuncs.profile.DefaultProfile defaultProfile() {
        return com.aliyuncs.profile.DefaultProfile.getProfile(aliyunProperties.getSms().getRegionId(), aliyunProperties.getAccessKeyId(), aliyunProperties.getAccessKeySecret());
    }

    @org.springframework.context.annotation.Bean
    com.aliyuncs.IAcsClient iAcsClient() {
        return new com.aliyuncs.DefaultAcsClient(defaultProfile());
    }
}