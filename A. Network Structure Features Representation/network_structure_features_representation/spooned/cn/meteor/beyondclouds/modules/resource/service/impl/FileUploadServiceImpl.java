package cn.meteor.beyondclouds.modules.resource.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 文件上传业务实现
 *
 * @author meteor
 */
@org.springframework.stereotype.Service
public class FileUploadServiceImpl implements cn.meteor.beyondclouds.modules.resource.service.IFileUploadService {
    private cn.meteor.beyondclouds.common.helper.IOssHelper ossHelper;

    private cn.meteor.beyondclouds.modules.resource.service.IUploadResourceService uploadResourceService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setOssHelper(cn.meteor.beyondclouds.common.helper.IOssHelper ossHelper, cn.meteor.beyondclouds.modules.resource.service.IUploadResourceService uploadResourceService) {
        this.ossHelper = ossHelper;
        this.uploadResourceService = uploadResourceService;
    }

    @java.lang.Override
    public java.lang.String upload(java.io.InputStream ins, long size, cn.meteor.beyondclouds.modules.resource.enums.UploadType uploadType, java.lang.String suffix, java.lang.String userId) throws cn.meteor.beyondclouds.modules.resource.exception.FileUploadServiceException {
        suffix = suffix.toLowerCase();
        // 1. 生成随机文件名
        java.lang.String filename = cn.meteor.beyondclouds.util.UUIDUtils.randomUUID() + suffix;
        java.lang.String uploadPath = uploadType.getBasePath() + filename;
        if (size > uploadType.getMaxSize()) {
            throw new cn.meteor.beyondclouds.modules.resource.exception.FileUploadServiceException(cn.meteor.beyondclouds.modules.resource.enums.FileUploadErrorCode.FILE_TOO_LARGE);
        }
        // 后缀错误
        if (!uploadType.getAcceptSuffixes().contains(suffix)) {
            throw new cn.meteor.beyondclouds.modules.resource.exception.FileUploadServiceException(cn.meteor.beyondclouds.modules.resource.enums.FileUploadErrorCode.ERROR_SUFFIX);
        }
        // 2. 上传文件到OSS
        try {
            // 3.把上传信息记录到数据库
            java.lang.String downloadUrl = ossHelper.upload(ins, uploadPath);
            cn.meteor.beyondclouds.modules.resource.entity.UploadResource uploadResource = new cn.meteor.beyondclouds.modules.resource.entity.UploadResource();
            uploadResource.setUserId(userId);
            uploadResource.setResourceType(uploadType.getType());
            uploadResource.setResourceUrl(downloadUrl);
            uploadResourceService.save(uploadResource);
            return downloadUrl;
        } catch (cn.meteor.beyondclouds.common.exception.OssException e) {
            e.printStackTrace();
            throw new cn.meteor.beyondclouds.modules.resource.exception.FileUploadServiceException(cn.meteor.beyondclouds.modules.resource.enums.FileUploadErrorCode.UPLOAD_FAILURE);
        }
    }
}