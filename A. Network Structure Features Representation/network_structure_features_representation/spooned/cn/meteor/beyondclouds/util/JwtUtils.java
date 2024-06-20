package cn.meteor.beyondclouds.util;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
/**
 * jwt 工具类
 *
 * @author meteor
 */
public class JwtUtils {
    private static final java.lang.String SECRET = "beyondclouds";

    /**
     * token 过期时间: 10天
     */
    public static final int calendarField = java.util.Calendar.DATE;

    public static final int calendarInterval = 10;

    /**
     * 生成jwt token
     *
     * @param claimMap
     * @return  */
    public static java.lang.String sign(java.util.Map<java.lang.String, java.lang.String> claimMap) {
        java.util.Calendar nowTime = java.util.Calendar.getInstance();
        nowTime.add(cn.meteor.beyondclouds.util.JwtUtils.calendarField, cn.meteor.beyondclouds.util.JwtUtils.calendarInterval);
        java.util.Date expiresDate = nowTime.getTime();
        com.auth0.jwt.JWTCreator.Builder builder = com.auth0.jwt.JWT.create();
        for (java.lang.String key : claimMap.keySet()) {
            builder.withClaim(key, claimMap.get(key));
        }
        java.lang.String jwtToken = builder.withExpiresAt(expiresDate).sign(com.auth0.jwt.algorithms.Algorithm.HMAC256(cn.meteor.beyondclouds.util.JwtUtils.SECRET));
        return jwtToken;
    }

    /**
     * 验证签名
     *
     * @param token
     * @return  */
    public static java.util.Map<java.lang.String, com.auth0.jwt.interfaces.Claim> verifyToken(java.lang.String token) {
        com.auth0.jwt.interfaces.DecodedJWT jwt = null;
        com.auth0.jwt.interfaces.JWTVerifier verifier = com.auth0.jwt.JWT.require(com.auth0.jwt.algorithms.Algorithm.HMAC256(cn.meteor.beyondclouds.util.JwtUtils.SECRET)).build();
        jwt = verifier.verify(token);
        return jwt.getClaims();
    }
}