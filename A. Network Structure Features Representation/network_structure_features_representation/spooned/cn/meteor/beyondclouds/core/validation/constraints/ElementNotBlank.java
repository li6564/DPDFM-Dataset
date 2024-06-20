package cn.meteor.beyondclouds.core.validation.constraints;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
/**
 * 被验证的参数集合的元素不能为空
 *
 * @author meteor
 */
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({ java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.ANNOTATION_TYPE, java.lang.annotation.ElementType.CONSTRUCTOR, java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.TYPE_USE })
@java.lang.annotation.Documented
@javax.validation.Constraint(validatedBy = cn.meteor.beyondclouds.core.validation.validator.ElementNotBlankValidator.class)
public @interface ElementNotBlank {
    java.lang.String message() default "element cannot blank";

    java.lang.Class<?>[] groups() default {  };

    java.lang.Class<? extends javax.validation.Payload>[] payload() default {  };

    @java.lang.annotation.Target({ java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.ANNOTATION_TYPE, java.lang.annotation.ElementType.CONSTRUCTOR, java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.TYPE_USE })
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
    @java.lang.annotation.Documented
    @interface List {
        cn.meteor.beyondclouds.core.validation.constraints.ElementNotBlank[] value();
    }
}