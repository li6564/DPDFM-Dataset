package cn.meteor.beyondclouds.modules.project.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
/**
 * <p>
 * 项目类别表 服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@org.springframework.stereotype.Service
public class ProjectCategoryServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.project.mapper.ProjectCategoryMapper, cn.meteor.beyondclouds.modules.project.entity.ProjectCategory> implements cn.meteor.beyondclouds.modules.project.service.IProjectCategoryService {
    @java.lang.Override
    public java.util.List<cn.meteor.beyondclouds.modules.project.entity.ProjectCategory> getProjectCategory() {
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.project.entity.ProjectCategory> projectCategoryQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        projectCategoryQueryWrapper.isNull(true, "parent_id");
        projectCategoryQueryWrapper.orderByDesc("priority");
        projectCategoryQueryWrapper.orderByAsc("category_id");
        return list(projectCategoryQueryWrapper);
    }
}