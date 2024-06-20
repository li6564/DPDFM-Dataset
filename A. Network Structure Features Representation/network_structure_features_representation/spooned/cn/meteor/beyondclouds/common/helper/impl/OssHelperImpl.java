package cn.meteor.beyondclouds.common.helper.impl;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * Oss文件上传辅助类
 *
 * @author 段启岩
 */
@org.springframework.stereotype.Component
public class OssHelperImpl implements cn.meteor.beyondclouds.common.helper.IOssHelper {
    private com.aliyun.oss.OSS ossClient;

    private cn.meteor.beyondclouds.config.properties.AliyunProperties aliyunProperties;

    @org.springframework.beans.factory.annotation.Autowired
    public void setAliyunProperties(com.aliyun.oss.OSS ossClient, cn.meteor.beyondclouds.config.properties.AliyunProperties aliyunProperties) {
        this.ossClient = ossClient;
        this.aliyunProperties = aliyunProperties;
    }

    @java.lang.Override
    public java.lang.String upload(java.io.InputStream ins, java.lang.String uploadTo) throws cn.meteor.beyondclouds.common.exception.OssException {
        try {
            com.aliyun.oss.model.PutObjectRequest putObjectRequest = new com.aliyun.oss.model.PutObjectRequest(aliyunProperties.getOss().getBucket(), uploadTo, ins);
            com.aliyun.oss.model.PutObjectResult result = ossClient.putObject(putObjectRequest);
            java.lang.String baseUrl = aliyunProperties.getOss().getBaseUrl();
            java.lang.String downloadUrl = (baseUrl.endsWith("/")) ? baseUrl + uploadTo : (baseUrl + "/") + uploadTo;
            return downloadUrl;
        } catch (java.lang.Exception e) {
            throw new cn.meteor.beyondclouds.common.exception.OssException("文件上传失败", e);
        }
    }

    @java.lang.Override
    public java.lang.String upload(byte[] bytes, java.lang.String uploadTo) throws cn.meteor.beyondclouds.common.exception.OssException {
        return upload(new java.io.ByteArrayInputStream(bytes), uploadTo);
    }
}