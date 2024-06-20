package cn.meteor.beyondclouds.modules.post.service;
import cn.meteor.beyondclouds.modules.post.exception.PostServiceException;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-02-20
 */
public interface IPostPraiseService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.post.entity.PostPraise> {
    /**
     * 动态以及动态评论的点赞
     *
     * @param currentUserId
     * @param postId
     * @throws PostServiceException
     */
    void postPraise(java.lang.String currentUserId, java.lang.String postId) throws cn.meteor.beyondclouds.modules.post.exception.PostServiceException;

    /**
     * 取消动态以及动态评论的点赞
     *
     * @param currentUserId
     * @param postId
     * @throws PostServiceException
     */
    void delPostPraise(java.lang.String currentUserId, java.lang.String postId) throws cn.meteor.beyondclouds.modules.post.exception.PostServiceException;

    /**
     * 动态点赞列表
     *
     * @param page
     * @param size
     * @param postId
     * @return  */
    cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.PraiseUserDTO> getPostPraises(java.lang.Integer page, java.lang.Integer size, java.lang.String postId);
}