package cn.meteor.beyondclouds.modules.resource.service;
/**
 * 文件上传业务
 *
 * @author 段启岩
 */
public interface IFileUploadService {
    /**
     * 上传文件
     *
     * @param ins
     * @param size
     * @param uploadType
     * @param suffix
     * @param userId
     * @return  */
    java.lang.String upload(java.io.InputStream ins, long size, cn.meteor.beyondclouds.modules.resource.enums.UploadType uploadType, java.lang.String suffix, java.lang.String userId) throws cn.meteor.beyondclouds.modules.resource.exception.FileUploadServiceException;
}