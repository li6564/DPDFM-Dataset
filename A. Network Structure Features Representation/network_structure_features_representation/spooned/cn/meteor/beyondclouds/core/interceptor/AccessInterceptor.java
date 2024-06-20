package cn.meteor.beyondclouds.core.interceptor;
import lombok.extern.java.Log;
import org.apache.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
/**
 * 访问拦截器
 * 判断当前访问者是否经过认证
 * 若没有经过认证，则阻止继续访问，并返回错误信息
 * 若经过认证，则直接放行
 *
 * @author meteor
 */
@lombok.extern.java.Log
public class AccessInterceptor implements org.springframework.web.servlet.HandlerInterceptor {
    private cn.meteor.beyondclouds.modules.user.service.IUserService userService;

    public AccessInterceptor(cn.meteor.beyondclouds.modules.user.service.IUserService userService) {
        this.userService = userService;
    }

    @java.lang.Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, java.lang.Object handler) throws java.lang.Exception {
        /**
         * 判断该接口是否允许匿名访问
         * 如果目标接口方法上有Anonymous注解，则直接放行
         */
        boolean allowAnonymous = false;
        if (((org.springframework.web.method.HandlerMethod) (handler)).hasMethodAnnotation(cn.meteor.beyondclouds.core.annotation.Anonymous.class)) {
            allowAnonymous = true;
        }
        // 1.若不需要认证或者用户认证成功则直接放行
        if (allowAnonymous) {
            return true;
        }
        cn.meteor.beyondclouds.core.authentication.Subject currentSubject = ((cn.meteor.beyondclouds.core.authentication.Subject) (cn.meteor.beyondclouds.util.ThreadLocalMap.get(cn.meteor.beyondclouds.core.constant.SysConstants.HTTP_ATTRIBUTE_SUBJECT)));
        // 2.目标接口需要认证且经过认证，直接放行
        if ((null != currentSubject) && currentSubject.isAuthenticated()) {
            // 用户如果被封号，则直接返回错误
            cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(currentSubject.getId());
            if (!user.getStatus().equals(cn.meteor.beyondclouds.modules.user.enums.UserStatus.NORMAL.getStatus())) {
                throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.USER_DISABLED);
            }
            return true;
        } else {
            // 3.目标接口需要认证且未经过认证，则抛出异常
            cn.meteor.beyondclouds.core.emuns.AuthorizationErrorCode authorizationErrorCode = ((cn.meteor.beyondclouds.core.emuns.AuthorizationErrorCode) (request.getAttribute(cn.meteor.beyondclouds.core.constant.SysConstants.AUTHORIZATION_ERROR_CODE)));
            if (null == authorizationErrorCode) {
                authorizationErrorCode = cn.meteor.beyondclouds.core.emuns.AuthorizationErrorCode.NON_HEADER_AUTHORIZATION;
            }
            // 设置http响应状态
            int httpStatus;
            switch (authorizationErrorCode) {
                case NON_HEADER_AUTHORIZATION :
                    httpStatus = org.apache.http.HttpStatus.SC_BAD_REQUEST;
                    break;
                case ILLEGAL_HEADER_AUTHORIZATION :
                    httpStatus = org.apache.http.HttpStatus.SC_BAD_REQUEST;
                    break;
                case SIGN_VERIFY_FAILURE :
                    httpStatus = org.apache.http.HttpStatus.SC_UNAUTHORIZED;
                    break;
                default :
                    httpStatus = org.apache.http.HttpStatus.SC_UNAUTHORIZED;
            }
            response.setStatus(httpStatus);
            // 抛出异常
            throw new cn.meteor.beyondclouds.modules.user.exception.AuthenticationServiceException(authorizationErrorCode);
        }
    }
}