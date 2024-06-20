package cn.meteor.beyondclouds.common.helper;
import cn.meteor.beyondclouds.common.exception.OssException;
/**
 * 阿里对象存储辅助类
 *
 * @author meteor
 */
public interface IOssHelper {
    /**
     * 上传文件
     *
     * @param ins
     * 		待上传文件的输入流
     * @param uploadTo
     * 		要上传到的路径
     * @return 文件上传完比后的访问url
     * @throws OssException
     */
    java.lang.String upload(java.io.InputStream ins, java.lang.String uploadTo) throws cn.meteor.beyondclouds.common.exception.OssException;

    /**
     * 上传文件
     *
     * @param bytes
     * 		待上传文件的字节
     * @param uploadTo
     * 		要上传到的路径
     * @return 文件上传完比后的访问url
     * @throws OssException
     */
    java.lang.String upload(byte[] bytes, java.lang.String uploadTo) throws cn.meteor.beyondclouds.common.exception.OssException;
}