package cn.meteor.beyondclouds.core.constant;
/**
 * 系统常量
 *
 * @author meteor
 */
public class SysConstants {
    /**
     * 在jwt生成token里面存储subject的id的字段名称
     */
    public static final java.lang.String CLAIM_SUBJECT_ID = "id";

    /**
     * 存储在HttpServletRequest里面当前登录的subject的键
     */
    public static final java.lang.String HTTP_ATTRIBUTE_SUBJECT = "current_subject";

    /**
     * 存储在HttpServletRequest里面当前的用户访问信息
     */
    public static final java.lang.String HTTP_ATTRIBUTE_ACCESS_INFO = "access_info";

    /**
     * 存储在HttpServletRequest里面的认证错误的信息
     */
    public static final java.lang.String AUTHORIZATION_ERROR_CODE = "AUTHORIZATION_ERROR_CODE";

    public static final long DEFAULT_MAX_SIZE = (1024 * 1024) * 3;

    public static final java.util.Set<java.lang.String> SUFFIXES_OF_IMAGE = java.util.Set.of(".bmp", ".jpeg", ".jpg", ".png", ".tif", ".gif", ".pcx", ".tga", ".exif", ".fpx", ".svg");

    public static final java.util.Set<java.lang.String> SUFFIXES_OF_VIDEO = java.util.Set.of(".mp4", ".oog", ".webm");

    /**
     */
    public static final java.lang.String THREAD_LOCAL_KEY_PREVENT_DUPLICATE_REDIS_LOCK = "THREAD_LOCAL_KEY_PREVENT_DUPLICATE_REDIS_LOCK";

    public static final java.lang.String SYS_ID = "SYS";

    public static final java.lang.String SYS_NAME = "系统";
}