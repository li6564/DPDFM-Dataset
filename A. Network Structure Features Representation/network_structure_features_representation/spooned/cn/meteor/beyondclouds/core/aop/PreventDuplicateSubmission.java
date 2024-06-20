package cn.meteor.beyondclouds.core.aop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.cors.CorsUtils;
/**
 *
 * @author meteor
 */
@org.aspectj.lang.annotation.Aspect
@org.springframework.stereotype.Component
@lombok.extern.slf4j.Slf4j
public class PreventDuplicateSubmission {
    private cn.meteor.beyondclouds.config.properties.BeyondCloudsProperties beyondCloudsProperties;

    private cn.meteor.beyondclouds.core.lock.RedisLockFactory redisLockFactory;

    @org.springframework.beans.factory.annotation.Autowired
    public PreventDuplicateSubmission(cn.meteor.beyondclouds.core.lock.RedisLockFactory redisLockFactory, cn.meteor.beyondclouds.config.properties.BeyondCloudsProperties beyondCloudsProperties) {
        this.redisLockFactory = redisLockFactory;
        this.beyondCloudsProperties = beyondCloudsProperties;
    }

    @org.aspectj.lang.annotation.Around("pointCutPreventDuplicateAnnotation()")
    public java.lang.Object processTx(org.aspectj.lang.ProceedingJoinPoint jp) throws java.lang.Throwable {
        if (!beyondCloudsProperties.getGlobalPreventDuplicate()) {
            log.info("global prevent duplicate closed.");
            return process(jp);
        } else {
            return jp.proceed();
        }
    }

    @org.aspectj.lang.annotation.Around("pointCutRequestMapping() || pointCutGetMapping() || pointCutPutMapping() || pointCutPostMapping() || pointCutDeleteMapping()")
    public java.lang.Object processGlobalPreventDu(org.aspectj.lang.ProceedingJoinPoint jp) throws java.lang.Throwable {
        if (beyondCloudsProperties.getGlobalPreventDuplicate()) {
            log.info("global prevent duplicate opened.");
            return process(jp);
        } else {
            return jp.proceed();
        }
    }

    @org.aspectj.lang.annotation.Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void pointCutRequestMapping() {
    }

    @org.aspectj.lang.annotation.Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void pointCutGetMapping() {
    }

    @org.aspectj.lang.annotation.Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void pointCutPutMapping() {
    }

    @org.aspectj.lang.annotation.Pointcut("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void pointCutDeleteMapping() {
    }

    @org.aspectj.lang.annotation.Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void pointCutPostMapping() {
    }

    @org.aspectj.lang.annotation.Pointcut("@annotation(cn.meteor.beyondclouds.core.annotation.PreventDuplicate)")
    public void pointCutPreventDuplicateAnnotation() {
    }

    private java.lang.Object process(org.aspectj.lang.ProceedingJoinPoint jp) throws java.lang.Throwable {
        java.lang.String token = jp.getSignature().toString();
        token = (cn.meteor.beyondclouds.util.SubjectUtils.getSubject().getIdentification() + ":") + token;
        javax.servlet.http.HttpServletRequest request = ((org.springframework.web.context.request.ServletRequestAttributes) (org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes())).getRequest();
        if (null != request) {
            token += request.getRequestURI();
        }
        java.util.concurrent.locks.Lock lock = redisLockFactory.createLock(cn.meteor.beyondclouds.util.Md5Utils.encode(token));
        if (lock.tryLock()) {
            try {
                return jp.proceed();
            } catch (java.lang.Exception e) {
                throw e;
            } finally {
                lock.unlock();
            }
        } else {
            log.info("DuplicateSubmission:{}", token);
            return cn.meteor.beyondclouds.core.api.Response.error(cn.meteor.beyondclouds.common.enums.ErrorCode.DUPLICATE_SUBMISSION);
        }
    }
}