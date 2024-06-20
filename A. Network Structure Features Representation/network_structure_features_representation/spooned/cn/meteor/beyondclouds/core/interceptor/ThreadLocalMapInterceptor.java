package cn.meteor.beyondclouds.core.interceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
/**
 *
 * @author meteor
 */
public class ThreadLocalMapInterceptor implements org.springframework.web.servlet.HandlerInterceptor {
    @java.lang.Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, java.lang.Object handler) throws java.lang.Exception {
        cn.meteor.beyondclouds.util.ThreadLocalMap.remove();
        return true;
    }

    @java.lang.Override
    public void postHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, java.lang.Object handler, org.springframework.web.servlet.ModelAndView modelAndView) throws java.lang.Exception {
        cn.meteor.beyondclouds.util.ThreadLocalMap.remove();
    }
}