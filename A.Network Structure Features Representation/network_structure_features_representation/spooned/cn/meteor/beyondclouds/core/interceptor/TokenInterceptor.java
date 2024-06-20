package cn.meteor.beyondclouds.core.interceptor;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
/**
 * Token拦截器,根据token信息解析出subject
 * 如果没有携带认证信息或认证信息错误，则在HttpServlet上下文设置一个匿名的Subject，并设置认证的错误信息，然后对请求进行放行
 * 如果解析到正确的token，则在HttpServlet上下文设置一个经过认证的Subject，然后对请求进行放行
 *
 * @author meteor
 */
public class TokenInterceptor implements org.springframework.web.servlet.HandlerInterceptor {
    private static final java.lang.String BEARER_AUTHORIZATION_START = "Bearer";

    private cn.meteor.beyondclouds.core.redis.TokenManager tokenManager;

    public TokenInterceptor(cn.meteor.beyondclouds.core.redis.TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @java.lang.Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, java.lang.Object handler) throws java.lang.Exception {
        java.lang.String authorization = request.getHeader(cn.meteor.beyondclouds.core.constant.HttpRequestHeaderNames.AUTHORIZATION);
        // 匿名subject
        cn.meteor.beyondclouds.core.authentication.Subject anonymousSubject = cn.meteor.beyondclouds.core.authentication.Subject.anonymous(cn.meteor.beyondclouds.util.RequestUtils.getIpAddr(request));
        // 1.未找到认证信息
        if (org.springframework.util.StringUtils.isEmpty(authorization)) {
            cn.meteor.beyondclouds.util.ThreadLocalMap.put(cn.meteor.beyondclouds.core.constant.SysConstants.HTTP_ATTRIBUTE_SUBJECT, anonymousSubject);
            request.setAttribute(cn.meteor.beyondclouds.core.constant.SysConstants.AUTHORIZATION_ERROR_CODE, cn.meteor.beyondclouds.core.emuns.AuthorizationErrorCode.NON_HEADER_AUTHORIZATION);
            return true;
        }
        // 2.找到认证信息但是认证信息非法
        if ((!authorization.startsWith(cn.meteor.beyondclouds.core.interceptor.TokenInterceptor.BEARER_AUTHORIZATION_START)) || (authorization.length() <= (cn.meteor.beyondclouds.core.interceptor.TokenInterceptor.BEARER_AUTHORIZATION_START.length() + 1))) {
            cn.meteor.beyondclouds.util.ThreadLocalMap.put(cn.meteor.beyondclouds.core.constant.SysConstants.HTTP_ATTRIBUTE_SUBJECT, anonymousSubject);
            request.setAttribute(cn.meteor.beyondclouds.core.constant.SysConstants.AUTHORIZATION_ERROR_CODE, cn.meteor.beyondclouds.core.emuns.AuthorizationErrorCode.ILLEGAL_HEADER_AUTHORIZATION);
            return true;
        }
        // 3.找到认证信息，进行认证信息正确性校验,并生成Subject
        try {
            // 获取token
            java.lang.String token = authorization.substring(cn.meteor.beyondclouds.core.interceptor.TokenInterceptor.BEARER_AUTHORIZATION_START.length() + 1);
            cn.meteor.beyondclouds.modules.user.dto.TokenInfo tokenInfo = tokenManager.getTokenInfo(token);
            if (null == tokenInfo) {
                throw new cn.meteor.beyondclouds.core.exception.AuthorizationException(cn.meteor.beyondclouds.core.emuns.AuthorizationErrorCode.SIGN_VERIFY_FAILURE);
            }
            // 构建一个经过系统认证的subject
            cn.meteor.beyondclouds.core.authentication.Subject authenticatedSubject = cn.meteor.beyondclouds.core.authentication.Subject.authenticated(tokenInfo.getUserId(), tokenInfo.getClientType(), cn.meteor.beyondclouds.util.RequestUtils.getIpAddr(request));
            cn.meteor.beyondclouds.util.ThreadLocalMap.put(cn.meteor.beyondclouds.core.constant.SysConstants.HTTP_ATTRIBUTE_SUBJECT, authenticatedSubject);
            return true;
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            // token校验失败
            cn.meteor.beyondclouds.util.ThreadLocalMap.put(cn.meteor.beyondclouds.core.constant.SysConstants.HTTP_ATTRIBUTE_SUBJECT, anonymousSubject);
            request.setAttribute(cn.meteor.beyondclouds.core.constant.SysConstants.AUTHORIZATION_ERROR_CODE, cn.meteor.beyondclouds.core.emuns.AuthorizationErrorCode.SIGN_VERIFY_FAILURE);
            return true;
        }
    }
}