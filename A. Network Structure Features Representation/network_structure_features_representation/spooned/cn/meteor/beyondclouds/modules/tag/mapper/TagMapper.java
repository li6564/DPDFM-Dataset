package cn.meteor.beyondclouds.modules.tag.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;
/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@org.springframework.stereotype.Component
public interface TagMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<cn.meteor.beyondclouds.modules.tag.entity.Tag> {
    cn.meteor.beyondclouds.modules.tag.entity.Tag getTags(java.lang.String tagName, java.lang.Integer tagType);

    java.util.List<cn.meteor.beyondclouds.modules.tag.entity.Tag> searchTags(java.lang.String keywords);
}