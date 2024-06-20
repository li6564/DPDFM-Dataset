package cn.meteor.beyondclouds.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * Swagger2配置类
 *
 * @author meteor
 */
@springfox.documentation.swagger2.annotations.EnableSwagger2
@org.springframework.context.annotation.Configuration
public class SwaggerConfig {
    @org.springframework.beans.factory.annotation.Value("${swagger.enable}")
    private boolean swaggerEnable;

    private springfox.documentation.service.ApiInfo apiInfo() {
        return new springfox.documentation.builders.ApiInfoBuilder().title("云里云外开源社区Api文档").description("云里云外开源社区").version("1.0.0").build();
    }

    @org.springframework.context.annotation.Bean
    public springfox.documentation.spring.web.plugins.Docket createRestApi() {
        springfox.documentation.builders.ParameterBuilder parameterBuilder = new springfox.documentation.builders.ParameterBuilder();
        java.util.List<springfox.documentation.service.Parameter> parameters = new java.util.ArrayList<springfox.documentation.service.Parameter>();
        parameterBuilder.name("Authorization").description("认证信息").modelRef(new springfox.documentation.schema.ModelRef("string")).parameterType("header").defaultValue("Bearer ").required(false).build();
        parameters.add(parameterBuilder.build());
        return new springfox.documentation.spring.web.plugins.Docket(springfox.documentation.spi.DocumentationType.SWAGGER_2).enable(swaggerEnable).apiInfo(apiInfo()).globalOperationParameters(parameters).select().apis(springfox.documentation.builders.RequestHandlerSelectors.basePackage("cn.meteor.beyondclouds")).paths(springfox.documentation.builders.PathSelectors.any()).build();
    }
}