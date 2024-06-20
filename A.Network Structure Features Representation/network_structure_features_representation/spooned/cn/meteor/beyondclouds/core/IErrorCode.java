package cn.meteor.beyondclouds.core;
/**
 * 错误码接口
 *
 * @author meteor
 */
public interface IErrorCode {
    /**
     * 获取错误代码
     *
     * @return  */
    long code();

    /**
     * 获取错误信息
     *
     * @return  */
    java.lang.String msg();
}