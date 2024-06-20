package cn.meteor.beyondclouds.modules.project.service;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 * 项目类别表 服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
public interface IProjectCategoryService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.project.entity.ProjectCategory> {
    /**
     * 获取项目分类列表
     *
     * @return  */
    java.util.List<cn.meteor.beyondclouds.modules.project.entity.ProjectCategory> getProjectCategory();
}