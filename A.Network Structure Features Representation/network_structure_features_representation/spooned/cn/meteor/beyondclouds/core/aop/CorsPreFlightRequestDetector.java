package cn.meteor.beyondclouds.core.aop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
/**
 *
 * @author meteor
 */
@org.aspectj.lang.annotation.Aspect
@org.springframework.stereotype.Component
@lombok.extern.slf4j.Slf4j
public class CorsPreFlightRequestDetector {
    @org.aspectj.lang.annotation.Around("execution(boolean *..*.preHandle(..))")
    public java.lang.Object processTx(org.aspectj.lang.ProceedingJoinPoint jp) throws java.lang.Throwable {
        javax.servlet.http.HttpServletRequest request = ((javax.servlet.http.HttpServletRequest) (jp.getArgs()[0]));
        if ((request != null) && org.springframework.web.cors.CorsUtils.isPreFlightRequest(request)) {
            log.info("detected pre-flight-request:{}.", request.getRequestURL().toString());
            return true;
        } else {
            return jp.proceed();
        }
    }
}