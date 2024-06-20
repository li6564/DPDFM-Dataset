package cn.meteor.beyondclouds.modules.post.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-02-20
 */
@org.springframework.stereotype.Service
public class PostPraiseServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.post.mapper.PostPraiseMapper, cn.meteor.beyondclouds.modules.post.entity.PostPraise> implements cn.meteor.beyondclouds.modules.post.service.IPostPraiseService {
    private cn.meteor.beyondclouds.modules.post.service.IPostService postService;

    private cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService;

    private cn.meteor.beyondclouds.modules.post.mapper.PostPraiseMapper postPraiseMapper;

    @org.springframework.beans.factory.annotation.Autowired
    public void setPostService(cn.meteor.beyondclouds.modules.post.service.IPostService postService) {
        this.postService = postService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setMessageQueueService(cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService) {
        this.messageQueueService = messageQueueService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setPostPraiseMapper(cn.meteor.beyondclouds.modules.post.mapper.PostPraiseMapper postPraiseMapper) {
        this.postPraiseMapper = postPraiseMapper;
    }

    @java.lang.Override
    public void postPraise(java.lang.String currentUserId, java.lang.String postId) throws cn.meteor.beyondclouds.modules.post.exception.PostServiceException {
        // 1.判断动态是否存在
        cn.meteor.beyondclouds.modules.post.entity.Post post = postService.getById(postId);
        if (null == post) {
            throw new cn.meteor.beyondclouds.modules.post.exception.PostServiceException(cn.meteor.beyondclouds.modules.post.enums.PostErrorCode.POST_NOT_FOUND);
        }
        // 2. 判断用户是否给目标动态点过赞
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.post.entity.PostPraise> postPraiseQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        postPraiseQueryWrapper.eq("target_id", postId).eq("user_id", currentUserId).eq("target_type", cn.meteor.beyondclouds.modules.post.enums.PostPraiseType.POST_PRAISE.getPraiseType());
        cn.meteor.beyondclouds.modules.post.entity.PostPraise postPraise = getOne(postPraiseQueryWrapper);
        if (null != postPraise) {
            throw new cn.meteor.beyondclouds.modules.post.exception.PostServiceException(cn.meteor.beyondclouds.modules.post.enums.PostErrorCode.POST_PRAISE_EXIST);
        }
        // 4. 点赞
        postPraise = new cn.meteor.beyondclouds.modules.post.entity.PostPraise();
        postPraise.setUserId(currentUserId);
        postPraise.setTargetId(postId);
        postPraise.setTargetType(cn.meteor.beyondclouds.modules.post.enums.PostPraiseType.POST_PRAISE.getPraiseType());
        save(postPraise);
        // 更新获赞数量
        post.setPraiseNum(post.getPraiseNum() + 1);
        postService.updateById(post);
        messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.addMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.POST_PRAISE, postPraise.getPraiseId()));
    }

    @java.lang.Override
    public void delPostPraise(java.lang.String currentUserId, java.lang.String postId) throws cn.meteor.beyondclouds.modules.post.exception.PostServiceException {
        cn.meteor.beyondclouds.modules.post.entity.Post post = postService.getById(postId);
        if (null == post) {
            throw new cn.meteor.beyondclouds.modules.post.exception.PostServiceException(cn.meteor.beyondclouds.modules.post.enums.PostErrorCode.POST_NOT_FOUND);
        }
        // 1. 判断用户是否给目标动态或动态评论点过赞
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.post.entity.PostPraise> postPraiseQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        postPraiseQueryWrapper.eq("target_id", postId).eq("user_id", currentUserId).eq("target_type", cn.meteor.beyondclouds.modules.post.enums.PostPraiseType.POST_PRAISE.getPraiseType());
        cn.meteor.beyondclouds.modules.post.entity.PostPraise postPraise = getOne(postPraiseQueryWrapper);
        if (null == postPraise) {
            throw new cn.meteor.beyondclouds.modules.post.exception.PostServiceException(cn.meteor.beyondclouds.modules.post.enums.PostErrorCode.POST_PRAISE_NOTEXIST);
        }
        // 2. 删除点赞
        remove(postPraiseQueryWrapper);
        // 更新获赞数量
        post.setPraiseNum(post.getPraiseNum() - 1);
        postService.updateById(post);
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.PraiseUserDTO> getPostPraises(java.lang.Integer page, java.lang.Integer size, java.lang.String postId) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.user.dto.PraiseUserDTO> praiseUserDTOPage = postPraiseMapper.selectPraisePage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page(page, size), postId, cn.meteor.beyondclouds.modules.post.enums.PostPraiseType.POST_PRAISE.getPraiseType());
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.PraiseUserDTO> pageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>();
        cn.meteor.beyondclouds.util.PageUtils.copyMeta(praiseUserDTOPage, pageDTO);
        pageDTO.setDataList(praiseUserDTOPage.getRecords());
        return pageDTO;
    }
}