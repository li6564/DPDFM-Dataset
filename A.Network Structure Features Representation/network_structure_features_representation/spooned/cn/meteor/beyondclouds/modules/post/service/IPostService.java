package cn.meteor.beyondclouds.modules.post.service;
import cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 * 动态表 服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
public interface IPostService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.post.entity.Post> {
    /**
     * 发布动态
     *
     * @param post
     * @throws ProjectServiceException
     */
    void publishPost(cn.meteor.beyondclouds.modules.post.entity.Post post) throws cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException;

    /**
     * 删除动态
     *
     * @param postId
     * @param userId
     */
    void deletePost(java.lang.String postId, java.lang.String userId) throws cn.meteor.beyondclouds.modules.post.exception.PostServiceException;

    /**
     * 动态列表
     *
     * @param pageNumber
     * @param pageSize
     * @param type
     * @return  */
    cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.post.dto.PostDTO> getPostPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.Integer type);

    /**
     * 个人动态列表
     *
     * @param pageNumber
     * @param pageSize
     * @param userId
     * @return  */
    cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.post.dto.PostDTO> getUserPostPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String userId);

    /**
     * 更新动态里的用户头像
     *
     * @param userId
     */
    void updatePostUserAvatar(java.lang.String userId);

    /**
     * 更新动态里的用户昵称
     *
     * @param userId
     */
    void updatePostUserNick(java.lang.String userId);

    /**
     * 获取我关注用户的动态列表
     *
     * @param pageNumber
     * @param pageSize
     * @param userId
     * @return  */
    cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.post.dto.PostDTO> getFollowedPostPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String userId);

    /**
     * 推荐动态
     *
     * @param page
     * @param size
     * @return  */
    cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.post.dto.PostDTO> getRecommendPosts(java.lang.Integer page, java.lang.Integer size);

    /**
     * 查询话题下的推荐动态列表
     *
     * @param topicId
     * @param page
     * @param size
     * @return  */
    cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.post.dto.PostDTO> getRecommendPostsInTopic(java.lang.String topicId, java.lang.Integer page, java.lang.Integer size);
}