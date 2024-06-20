package cn.meteor.beyondclouds.core.resolver;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
/**
 *
 * @author meteor
 */
public class CurrentSubjectResolver implements org.springframework.web.method.support.HandlerMethodArgumentResolver {
    @java.lang.Override
    public boolean supportsParameter(org.springframework.core.MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(cn.meteor.beyondclouds.core.annotation.CurrentSubject.class);
    }

    @java.lang.Override
    public java.lang.Object resolveArgument(org.springframework.core.MethodParameter methodParameter, org.springframework.web.method.support.ModelAndViewContainer modelAndViewContainer, org.springframework.web.context.request.NativeWebRequest nativeWebRequest, org.springframework.web.bind.support.WebDataBinderFactory webDataBinderFactory) throws java.lang.Exception {
        return cn.meteor.beyondclouds.util.ThreadLocalMap.get(cn.meteor.beyondclouds.core.constant.SysConstants.HTTP_ATTRIBUTE_SUBJECT);
    }
}