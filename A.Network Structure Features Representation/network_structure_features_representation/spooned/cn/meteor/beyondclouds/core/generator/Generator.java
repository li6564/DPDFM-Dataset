package cn.meteor.beyondclouds.core.generator;
/**
 * 生成器接口
 *
 * @author meteor
 */
public interface Generator<T> {
    /**
     * 获取下一个生产的对象
     *
     * @return  */
    T next();
}