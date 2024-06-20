package cn.meteor.beyondclouds.core.redis;
/**
 * Redis键常量
 *
 * @author meteor
 */
public final class RedisKey {
    private static final java.lang.String KEY_PREFIX_MOBILE_VERIFY_CODE = "verify_code_mobile:";

    private static final java.lang.String KEY_PREFIX_EMAIL_VERIFY_CODE = "verify_code_email:";

    private static final java.lang.String KEY_PREFIX_EMAIL_ACTIVE_CODE = "email_active_code:";

    private static final java.lang.String KEY_PREFIX_USER_FOLLOWED = "user_followed:";

    private static final java.lang.String KEY_PREFIX_USER_FANS = "user_fans:";

    /**
     * 手机验证码
     *
     * @param mobile
     * @return  */
    public static java.lang.String MOBILE_VERIFY_CODE(java.lang.String mobile) {
        return cn.meteor.beyondclouds.core.redis.RedisKey.KEY_PREFIX_MOBILE_VERIFY_CODE + mobile;
    }

    /**
     * 邮箱验证码
     *
     * @param email
     * @return  */
    public static java.lang.String EMAIL_VERIFY_CODE(java.lang.String email) {
        return cn.meteor.beyondclouds.core.redis.RedisKey.KEY_PREFIX_EMAIL_VERIFY_CODE + email;
    }

    /**
     * 邮箱激活码
     *
     * @param activeCode
     * @return  */
    public static java.lang.String EMAIL_ACTIVE_CODE(java.lang.String activeCode) {
        return cn.meteor.beyondclouds.core.redis.RedisKey.KEY_PREFIX_EMAIL_ACTIVE_CODE + activeCode;
    }

    /**
     * 用户关注的人
     *
     * @param userId
     * @return  */
    public static java.lang.String USER_FOLLOWED(java.lang.String userId) {
        return cn.meteor.beyondclouds.core.redis.RedisKey.KEY_PREFIX_USER_FOLLOWED + userId;
    }

    /**
     * 用户的所有粉丝
     *
     * @param userId
     * @return  */
    public static java.lang.String USER_FANS(java.lang.String userId) {
        return cn.meteor.beyondclouds.core.redis.RedisKey.KEY_PREFIX_USER_FANS + userId;
    }

    public static java.lang.String IP_VISIT_COUNT(java.lang.String ipAddress) {
        return "IP_VISIT_COUNT:" + ipAddress;
    }

    public static java.lang.String IP_VISIT_LATEST_VISIT_TIME(java.lang.String ipAddress) {
        return "IP_VISIT_LATEST_VISIT_TIME:" + ipAddress;
    }

    public static java.lang.String IP_FILED_VISIT_COUNT(java.lang.String ipAddress, cn.meteor.beyondclouds.core.flow.ParamType paramType, java.lang.String paramValue) {
        return (((("IP_FILED_VISIT_COUNT:" + ipAddress) + ":") + paramType.name()) + ":") + paramValue;
    }

    public static java.lang.String IP_FILED_LATEST_VISIT_TIME(java.lang.String ipAddress, cn.meteor.beyondclouds.core.flow.ParamType paramType, java.lang.String paramValue) {
        return (((("IP_FILED_LATEST_VISIT_TIME:" + ipAddress) + ":") + paramType.name()) + ":") + paramValue;
    }

    public static java.lang.String USER_NICK_GEN(java.lang.String datePrefix) {
        return "USER_NICK_GEN:" + datePrefix;
    }

    public static java.lang.String USER_TOKEN_INFO(java.lang.String userId, java.lang.String clientType) {
        return (("USER_TO_TOKEN:" + clientType) + ":") + userId;
    }

    public static java.lang.String TOKEN_TO_USER(java.lang.String token) {
        return "TOKEN_TO_USER:" + token;
    }

    public static java.lang.String USER_REFRESH_TOKEN_INFO(java.lang.String userId, java.lang.String clientType) {
        return (("USER_TO_REFRESH_TOKEN:" + clientType) + ":") + userId;
    }

    public static java.lang.String REFRESH_TOKEN_TO_USER(java.lang.String token) {
        return "REFRESH_TOKEN_TO_USER:" + token;
    }
}