package cn.meteor.beyondclouds.core.annotation;
/**
 * Controller的参数注解，用来注入当前登录的用户
 *
 * @author meteor
 */
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target(java.lang.annotation.ElementType.PARAMETER)
public @interface CurrentSubject {}