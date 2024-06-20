package cn.meteor.beyondclouds.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;
/**
 *
 * @author meteor
 */
@org.springframework.context.annotation.Configuration
@cn.meteor.beyondclouds.config.EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    @org.springframework.beans.factory.annotation.Autowired
    private cn.meteor.beyondclouds.common.helper.IRedisHelper redisHelper;

    @org.springframework.beans.factory.annotation.Autowired
    private cn.meteor.beyondclouds.core.redis.TokenManager tokenManager;

    @org.springframework.beans.factory.annotation.Autowired
    private cn.meteor.beyondclouds.modules.user.service.IUserService userService;

    @org.springframework.context.annotation.Bean
    cn.meteor.beyondclouds.core.interceptor.TokenInterceptor tokenInterceptor() {
        return new cn.meteor.beyondclouds.core.interceptor.TokenInterceptor(tokenManager);
    }

    @org.springframework.context.annotation.Bean
    cn.meteor.beyondclouds.core.interceptor.AccessInterceptor accessInterceptor() {
        return new cn.meteor.beyondclouds.core.interceptor.AccessInterceptor(userService);
    }

    @org.springframework.context.annotation.Bean
    cn.meteor.beyondclouds.core.interceptor.IpAccessInfoCollectInterceptor ipFlowControlInterceptor() {
        return new cn.meteor.beyondclouds.core.interceptor.IpAccessInfoCollectInterceptor(redisHelper);
    }

    @org.springframework.context.annotation.Bean
    cn.meteor.beyondclouds.core.interceptor.ThreadLocalMapInterceptor threadLocalMapInterceptor() {
        return new cn.meteor.beyondclouds.core.interceptor.ThreadLocalMapInterceptor();
    }

    @java.lang.Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(threadLocalMapInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(tokenInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(accessInterceptor()).addPathPatterns("/**").excludePathPatterns("/error").excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
        registry.addInterceptor(ipFlowControlInterceptor()).addPathPatterns("/**");
    }

    @java.lang.Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @java.lang.Override
    public void addArgumentResolvers(java.util.List<org.springframework.web.method.support.HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new cn.meteor.beyondclouds.core.resolver.CurrentSubjectResolver());
        resolvers.add(new cn.meteor.beyondclouds.core.resolver.CollectAccessInfoResolver());
    }

    @java.lang.Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowCredentials(true).maxAge(3600).allowedHeaders("*");
    }
}