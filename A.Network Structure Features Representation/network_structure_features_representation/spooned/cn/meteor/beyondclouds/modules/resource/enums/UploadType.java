package cn.meteor.beyondclouds.modules.resource.enums;
import lombok.Getter;
/**
 * 上传的类型
 *
 * @author meteor
 */
@lombok.Getter
public enum UploadType {

    /**
     * 上传头像
     */
    AVATAR(0, "avatar/", cn.meteor.beyondclouds.core.constant.SysConstants.DEFAULT_MAX_SIZE, cn.meteor.beyondclouds.core.constant.SysConstants.SUFFIXES_OF_IMAGE),
    /**
     * 上传博客封面
     */
    BLOG_COVER(1, "blog/cover/", cn.meteor.beyondclouds.core.constant.SysConstants.DEFAULT_MAX_SIZE, cn.meteor.beyondclouds.core.constant.SysConstants.SUFFIXES_OF_IMAGE),
    /**
     * 上传项目封面
     */
    PROJECT_COVER(2, "project/cover/", cn.meteor.beyondclouds.core.constant.SysConstants.DEFAULT_MAX_SIZE, cn.meteor.beyondclouds.core.constant.SysConstants.SUFFIXES_OF_IMAGE),
    /**
     * 上传动态图片
     */
    TRENDS_IMAGES(3, "trends/images/", cn.meteor.beyondclouds.core.constant.SysConstants.DEFAULT_MAX_SIZE, cn.meteor.beyondclouds.core.constant.SysConstants.SUFFIXES_OF_IMAGE),
    /**
     * 上传内容图片
     */
    BLOG_IMAGES(4, "blog/images/", cn.meteor.beyondclouds.core.constant.SysConstants.DEFAULT_MAX_SIZE, cn.meteor.beyondclouds.core.constant.SysConstants.SUFFIXES_OF_IMAGE),
    /**
     * 上传项目内容图片
     */
    PROJECT_IMAGES(5, "project/images/", cn.meteor.beyondclouds.core.constant.SysConstants.DEFAULT_MAX_SIZE, cn.meteor.beyondclouds.core.constant.SysConstants.SUFFIXES_OF_IMAGE),
    /**
     * 上传问答内容图片
     */
    QUESTION_IMAGES(6, "question/images/", cn.meteor.beyondclouds.core.constant.SysConstants.DEFAULT_MAX_SIZE, cn.meteor.beyondclouds.core.constant.SysConstants.SUFFIXES_OF_IMAGE),
    /**
     * 上传CMS内容图片
     */
    CMS_IMAGES(100, "cms/images/", cn.meteor.beyondclouds.core.constant.SysConstants.DEFAULT_MAX_SIZE, cn.meteor.beyondclouds.core.constant.SysConstants.SUFFIXES_OF_IMAGE),
    /**
     * 上传动态图片
     */
    TRENDS_VIDEO(101, "trends/videos/", (1024 * 1024) * 10L, cn.meteor.beyondclouds.core.constant.SysConstants.SUFFIXES_OF_VIDEO);
    private int ordinal;

    private java.lang.String basePath;

    private java.lang.Long maxSize;

    private java.util.Set<java.lang.String> acceptSuffixes;

    UploadType(int ordinal, java.lang.String basePath, java.lang.Long maxSize, java.util.Set<java.lang.String> acceptSuffixes) {
        this.ordinal = ordinal;
        this.basePath = basePath;
        this.maxSize = maxSize;
        this.acceptSuffixes = acceptSuffixes;
    }

    public java.lang.String getBasePath() {
        return basePath;
    }

    public int getType() {
        return ordinal;
    }

    public static cn.meteor.beyondclouds.modules.resource.enums.UploadType valueOf(int type) {
        for (cn.meteor.beyondclouds.modules.resource.enums.UploadType uploadType : cn.meteor.beyondclouds.modules.resource.enums.UploadType.values()) {
            if (uploadType.getType() == type) {
                return uploadType;
            }
        }
        return null;
    }
}