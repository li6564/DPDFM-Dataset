package cn.meteor.beyondclouds.core.interceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
/**
 * IP访问统计拦截器
 *
 * @author meteor
 */
@lombok.extern.slf4j.Slf4j
public class IpAccessInfoCollectInterceptor implements org.springframework.web.servlet.HandlerInterceptor {
    private cn.meteor.beyondclouds.common.helper.IRedisHelper redisHelper;

    public IpAccessInfoCollectInterceptor(cn.meteor.beyondclouds.common.helper.IRedisHelper redisHelper) {
        this.redisHelper = redisHelper;
    }

    @java.lang.Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, java.lang.Object handler) throws java.lang.Exception {
        cn.meteor.beyondclouds.core.authentication.Subject currentSubject = cn.meteor.beyondclouds.util.SubjectUtils.getSubject();
        java.lang.String ipAddress = currentSubject.getIpAddress();
        if (!(handler instanceof org.springframework.web.method.HandlerMethod)) {
            return true;
        }
        int visitCount = getAndIncreaseVisitCount(ipAddress);
        java.lang.Long latestVisitTime = getAndSetLatestVisitTime(ipAddress);
        /**
         * 判断该接口是需要注入访问信息
         * 如果目标接口方法上有Anonymous注解，则直接放行
         */
        cn.meteor.beyondclouds.core.flow.CollectAccessInfo collectAccessInfo = searchInjectInfo(((org.springframework.web.method.HandlerMethod) (handler)).getMethod());
        if (null != collectAccessInfo) {
            log.info("found injectAccessInfo:" + collectAccessInfo.toString());
            java.lang.String paramName = collectAccessInfo.paramName();
            cn.meteor.beyondclouds.core.flow.ParamType paramType = collectAccessInfo.type();
            cn.meteor.beyondclouds.core.flow.TransmitType transmitType = collectAccessInfo.transmitType();
            long timeout = collectAccessInfo.timeout();
            java.lang.String paramValue = resolveParamValue(request, paramName, transmitType);
            cn.meteor.beyondclouds.core.flow.AccessInfo accessInfo = getAccessInfo(ipAddress, paramType, paramValue, timeout);
            accessInfo.setVisitCount(visitCount);
            accessInfo.setLatestVisitTime(latestVisitTime);
            request.setAttribute(cn.meteor.beyondclouds.core.constant.SysConstants.HTTP_ATTRIBUTE_ACCESS_INFO, accessInfo);
        }
        return true;
    }

    private cn.meteor.beyondclouds.core.flow.AccessInfo getAccessInfo(java.lang.String ipAddress, cn.meteor.beyondclouds.core.flow.ParamType paramType, java.lang.String paramValue, long timeout) {
        cn.meteor.beyondclouds.core.flow.AccessInfo accessInfo = new cn.meteor.beyondclouds.core.flow.AccessInfo();
        accessInfo.setIpAddress(ipAddress);
        if (!org.springframework.util.StringUtils.isEmpty(paramValue)) {
            accessInfo.setFieldVisitCount(getAndIncreaseFieldVisitCount(ipAddress, paramType, paramValue, timeout));
            accessInfo.setFieldLatestVisitTime(getAndSetFieldLatestVisitTime(ipAddress, paramType, paramValue, timeout));
        }
        return accessInfo;
    }

    /**
     * 获取字段的最后访问时间然后重新实则
     *
     * @param ipAddress
     * @param paramType
     * @param paramValue
     * @return  */
    private java.lang.Long getAndSetFieldLatestVisitTime(java.lang.String ipAddress, cn.meteor.beyondclouds.core.flow.ParamType paramType, java.lang.String paramValue, long timeout) {
        java.lang.Long latestVisitTime = redisHelper.get(cn.meteor.beyondclouds.core.redis.RedisKey.IP_FILED_LATEST_VISIT_TIME(ipAddress, paramType, paramValue), java.lang.Long.class);
        redisHelper.set(cn.meteor.beyondclouds.core.redis.RedisKey.IP_FILED_LATEST_VISIT_TIME(ipAddress, paramType, paramValue), java.lang.System.currentTimeMillis(), timeout);
        return latestVisitTime;
    }

    /**
     * 获取字段的访问次数然后+1
     *
     * @param ipAddress
     * @param paramType
     * @param paramValue
     * @return  */
    private int getAndIncreaseFieldVisitCount(java.lang.String ipAddress, cn.meteor.beyondclouds.core.flow.ParamType paramType, java.lang.String paramValue, long timeout) {
        java.lang.Integer visitCount = redisHelper.get(cn.meteor.beyondclouds.core.redis.RedisKey.IP_FILED_VISIT_COUNT(ipAddress, paramType, paramValue), java.lang.Integer.class);
        if (null == visitCount) {
            redisHelper.set(cn.meteor.beyondclouds.core.redis.RedisKey.IP_FILED_VISIT_COUNT(ipAddress, paramType, paramValue), 1, timeout);
            return 0;
        } else {
            redisHelper.set(cn.meteor.beyondclouds.core.redis.RedisKey.IP_FILED_VISIT_COUNT(ipAddress, paramType, paramValue), visitCount + 1, timeout);
            return visitCount;
        }
    }

    /**
     * 获取访问次数然后对访问次数+1
     *
     * @param ipAddress
     * @return  */
    private java.lang.Integer getAndIncreaseVisitCount(java.lang.String ipAddress) {
        java.lang.Integer visitCount = redisHelper.get(cn.meteor.beyondclouds.core.redis.RedisKey.IP_VISIT_COUNT(ipAddress), java.lang.Integer.class);
        if (null == visitCount) {
            redisHelper.set(cn.meteor.beyondclouds.core.redis.RedisKey.IP_VISIT_COUNT(ipAddress), 1, 60 * 10);
            return 0;
        } else {
            redisHelper.set(cn.meteor.beyondclouds.core.redis.RedisKey.IP_VISIT_COUNT(ipAddress), visitCount + 1, 60 * 10);
            return visitCount;
        }
    }

    /**
     * 获取最后访问时间然后更新最新访问时间
     *
     * @param ipAddress
     * @return  */
    private java.lang.Long getAndSetLatestVisitTime(java.lang.String ipAddress) {
        java.lang.Long latestVisitTime = redisHelper.get(cn.meteor.beyondclouds.core.redis.RedisKey.IP_VISIT_LATEST_VISIT_TIME(ipAddress), java.lang.Long.class);
        redisHelper.set(cn.meteor.beyondclouds.core.redis.RedisKey.IP_VISIT_LATEST_VISIT_TIME(ipAddress), java.lang.System.currentTimeMillis(), 60 * 10);
        return latestVisitTime;
    }

    private java.lang.String resolveParamValue(javax.servlet.http.HttpServletRequest request, java.lang.String paramName, cn.meteor.beyondclouds.core.flow.TransmitType transmitType) {
        java.lang.String paramValue = null;
        switch (transmitType) {
            case PATH :
                java.util.Map pathVariables = ((java.util.Map) (request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE)));
                paramValue = ((java.lang.String) (pathVariables.get(paramName)));
                break;
            case PARAM :
                paramValue = request.getParameter(paramName);
        }
        return paramValue;
    }

    private cn.meteor.beyondclouds.core.flow.CollectAccessInfo searchInjectInfo(java.lang.reflect.Method method) {
        for (java.lang.reflect.Parameter parameter : method.getParameters()) {
            if (null != parameter.getAnnotation(cn.meteor.beyondclouds.core.flow.CollectAccessInfo.class)) {
                return parameter.getAnnotation(cn.meteor.beyondclouds.core.flow.CollectAccessInfo.class);
            }
        }
        return null;
    }
}