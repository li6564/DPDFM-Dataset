package cn.meteor.beyondclouds.core.annotation;
/**
 * 在接口上加入此注解表示该接口对所有用户是开放的
 *
 * @author 段启岩
 */
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target(java.lang.annotation.ElementType.METHOD)
public @interface Anonymous {}