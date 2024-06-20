package cn.meteor.beyondclouds.core.annotation;
/**
 *
 * @author meteor
 */
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target(java.lang.annotation.ElementType.METHOD)
public @interface ReplaceWithRemarks {
    java.lang.String id() default "userId";

    java.lang.String[] fields() default { "userNick", "nickName" };
}