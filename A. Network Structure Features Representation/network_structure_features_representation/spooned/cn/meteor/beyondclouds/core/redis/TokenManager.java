package cn.meteor.beyondclouds.core.redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 *
 * @author meteor
 */
@org.springframework.stereotype.Component
public class TokenManager {
    private cn.meteor.beyondclouds.common.helper.IRedisHelper redisHelper;

    private static final int DEFAULT_EXPIRE = (60 * 60) * 2;

    private static final int REFRESH_DEFAULT_EXPIRE = cn.meteor.beyondclouds.core.redis.TokenManager.DEFAULT_EXPIRE * 2;

    private static final int ANDROID_DEFAULT_EXPIRE = (60 * 60) * 24;

    private static final int ANDROID_REFRESH_DEFAULT_EXPIRE = ((60 * 60) * 24) * 10;

    @org.springframework.beans.factory.annotation.Autowired
    public void setRedisHelper(cn.meteor.beyondclouds.common.helper.IRedisHelper redisHelper) {
        this.redisHelper = redisHelper;
    }

    /**
     * 生成token
     *
     * @param userId
     * @param clientType
     * @return  */
    public cn.meteor.beyondclouds.modules.user.dto.TokenResult generateToken(java.lang.String userId, cn.meteor.beyondclouds.modules.user.enums.ClientType clientType) {
        // 生成新token
        java.lang.String newToken = cn.meteor.beyondclouds.util.UUIDUtils.randomToken();
        java.lang.String newRefreshToken = cn.meteor.beyondclouds.util.UUIDUtils.randomToken();
        // 如果不是web端，则删除旧token和refresh_token
        removeTokenAndRefreshToken(userId, clientType);
        // 存储新token和新的refresh_token
        int tokenExpire;
        int refreshTokenExpire;
        switch (clientType) {
            case ANDROID :
                tokenExpire = cn.meteor.beyondclouds.core.redis.TokenManager.ANDROID_DEFAULT_EXPIRE;
                refreshTokenExpire = cn.meteor.beyondclouds.core.redis.TokenManager.ANDROID_REFRESH_DEFAULT_EXPIRE;
                break;
            case WEB :
            default :
                tokenExpire = cn.meteor.beyondclouds.core.redis.TokenManager.DEFAULT_EXPIRE;
                refreshTokenExpire = cn.meteor.beyondclouds.core.redis.TokenManager.REFRESH_DEFAULT_EXPIRE;
        }
        redisHelper.set(cn.meteor.beyondclouds.core.redis.RedisKey.TOKEN_TO_USER(newToken), cn.meteor.beyondclouds.modules.user.dto.TokenInfo.create(newToken, userId, clientType.getType()), tokenExpire);
        redisHelper.set(cn.meteor.beyondclouds.core.redis.RedisKey.USER_TOKEN_INFO(userId, clientType.name()), newToken, tokenExpire);
        redisHelper.set(cn.meteor.beyondclouds.core.redis.RedisKey.REFRESH_TOKEN_TO_USER(newRefreshToken), cn.meteor.beyondclouds.modules.user.dto.TokenInfo.create(newRefreshToken, userId, clientType.getType()), refreshTokenExpire);
        redisHelper.set(cn.meteor.beyondclouds.core.redis.RedisKey.USER_REFRESH_TOKEN_INFO(userId, clientType.name()), newRefreshToken, refreshTokenExpire);
        return new cn.meteor.beyondclouds.modules.user.dto.TokenResult(newToken, newRefreshToken);
    }

    /**
     * 根据token获取token信息
     *
     * @param token
     * @return  */
    public cn.meteor.beyondclouds.modules.user.dto.TokenInfo getTokenInfo(java.lang.String token) {
        cn.meteor.beyondclouds.modules.user.dto.TokenInfo tokenInfo = redisHelper.get(cn.meteor.beyondclouds.core.redis.RedisKey.TOKEN_TO_USER(token), cn.meteor.beyondclouds.modules.user.dto.TokenInfo.class);
        // 如果是web端，则刷新token
        if ((null != tokenInfo) && (cn.meteor.beyondclouds.modules.user.enums.ClientType.valueOf(tokenInfo.getClientType()) == cn.meteor.beyondclouds.modules.user.enums.ClientType.WEB)) {
            // 刷新token过期时间
            redisHelper.expire(cn.meteor.beyondclouds.core.redis.RedisKey.TOKEN_TO_USER(token), cn.meteor.beyondclouds.core.redis.TokenManager.DEFAULT_EXPIRE);
            redisHelper.expire(cn.meteor.beyondclouds.core.redis.RedisKey.USER_TOKEN_INFO(tokenInfo.getUserId(), cn.meteor.beyondclouds.modules.user.enums.ClientType.WEB.name()), cn.meteor.beyondclouds.core.redis.TokenManager.DEFAULT_EXPIRE);
        }
        return tokenInfo;
    }

    /**
     * 根据refreshToken获取refreshToken信息
     *
     * @param refreshToken
     * @return  */
    public cn.meteor.beyondclouds.modules.user.dto.TokenInfo getRefreshTokenInfo(java.lang.String refreshToken) {
        cn.meteor.beyondclouds.modules.user.dto.TokenInfo tokenInfo = redisHelper.get(cn.meteor.beyondclouds.core.redis.RedisKey.REFRESH_TOKEN_TO_USER(refreshToken), cn.meteor.beyondclouds.modules.user.dto.TokenInfo.class);
        return tokenInfo;
    }

    /**
     * 删除token和refreshToken
     *
     * @param userId
     * @param clientType
     */
    public void removeTokenAndRefreshToken(java.lang.String userId, cn.meteor.beyondclouds.modules.user.enums.ClientType clientType) {
        // 删除旧token和refresh_token
        java.lang.String oldToken = redisHelper.get(cn.meteor.beyondclouds.core.redis.RedisKey.USER_TOKEN_INFO(userId, clientType.name()));
        java.lang.String oldRefreshToken = redisHelper.get(cn.meteor.beyondclouds.core.redis.RedisKey.USER_REFRESH_TOKEN_INFO(userId, clientType.name()));
        if (null != oldToken) {
            redisHelper.del(cn.meteor.beyondclouds.core.redis.RedisKey.TOKEN_TO_USER(oldToken), cn.meteor.beyondclouds.core.redis.RedisKey.USER_TOKEN_INFO(userId, clientType.name()));
        }
        if (null != oldRefreshToken) {
            redisHelper.del(cn.meteor.beyondclouds.core.redis.RedisKey.REFRESH_TOKEN_TO_USER(oldRefreshToken), cn.meteor.beyondclouds.core.redis.RedisKey.USER_REFRESH_TOKEN_INFO(userId, clientType.name()));
        }
    }

    /**
     * 判断用户是否登录web端
     *
     * @param userId
     * @return  */
    public boolean isLoginToWeb(java.lang.String userId) {
        return null != redisHelper.get(cn.meteor.beyondclouds.core.redis.RedisKey.USER_TOKEN_INFO(userId, cn.meteor.beyondclouds.modules.user.enums.ClientType.WEB.name()));
    }
}