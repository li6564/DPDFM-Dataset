package cn.meteor.beyondclouds.core.flow;
/**
 *
 * @author meteor
 */
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target(java.lang.annotation.ElementType.PARAMETER)
public @interface CollectAccessInfo {
    /**
     * 要访问的目标的类型
     *
     * @return  */
    cn.meteor.beyondclouds.core.flow.ParamType type();

    /**
     * 要访问的目标的ID在请求中的名称
     *
     * @return  */
    java.lang.String paramName();

    /**
     * 要访问的目标的ID在请求中的类型
     *
     * @return  */
    cn.meteor.beyondclouds.core.flow.TransmitType transmitType() default cn.meteor.beyondclouds.core.flow.TransmitType.PATH;

    long timeout() default 10 * 60;
}