package cn.meteor.beyondclouds.modules.question.util;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 *
 * @author 胡学良
 * @since 2020/1/31
 */
public class QuestionUtils {
    /**
     * 获取相关的条件构造器
     *
     * @param field
     * 		表中的字段名称
     * @param data
     * 		比较数据
     * @param <T>
     * @return  */
    public static <T> com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<T> getWrapper(java.lang.String field, java.lang.Object data) {
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<T> queryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        queryWrapper.eq(field, data);
        return queryWrapper;
    }
}