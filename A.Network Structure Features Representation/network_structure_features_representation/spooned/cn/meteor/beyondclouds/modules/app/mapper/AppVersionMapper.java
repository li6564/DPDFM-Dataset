package cn.meteor.beyondclouds.modules.app.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 段启岩
 * @since 2020-03-19
 */
public interface AppVersionMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<cn.meteor.beyondclouds.modules.app.entity.AppVersion> {
    /**
     * 通过sql语句根据时间降序排序数据后，限制返回最新一条
     *
     * @return  */
    cn.meteor.beyondclouds.modules.app.entity.AppVersion getLatestVersion();
}