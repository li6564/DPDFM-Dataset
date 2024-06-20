package cn.meteor.beyondclouds.modules.user.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-02-12
 */
@org.springframework.stereotype.Service
public class UserRemarksServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.user.mapper.UserRemarksMapper, cn.meteor.beyondclouds.modules.user.entity.UserRemarks> implements cn.meteor.beyondclouds.modules.user.service.IUserRemarksService {
    @java.lang.Override
    public void alterRemarks(java.lang.String currentUserId, java.lang.String userId, java.lang.String remarks) {
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper queryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
        queryWrapper.eq("user_id", currentUserId);
        queryWrapper.eq("marked_id", userId);
        // 如果传入的昵称是空的，则删除备注
        if (org.apache.commons.lang3.StringUtils.isBlank(remarks)) {
            remove(queryWrapper);
            return;
        }
        // 从数据库查询备注信息
        cn.meteor.beyondclouds.modules.user.entity.UserRemarks userRemarks = getOne(queryWrapper);
        if (null == userRemarks) {
            // 如果以前没有记录，则新增一条
            userRemarks = new cn.meteor.beyondclouds.modules.user.entity.UserRemarks();
            userRemarks.setUserId(currentUserId);
            userRemarks.setMarkedId(userId);
            userRemarks.setRemarks(remarks);
            save(userRemarks);
        } else {
            // 否则直接更新即可
            userRemarks.setRemarks(remarks);
            updateById(userRemarks);
        }
    }
}