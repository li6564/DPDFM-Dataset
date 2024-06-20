package cn.meteor.beyondclouds.modules.message.listener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 *
 * @author meteor
 */
@org.springframework.stereotype.Component
public class MessageListener implements cn.meteor.beyondclouds.core.listener.DataItemChangeListener {
    private static final java.util.regex.Pattern LINK_PATTERN = java.util.regex.Pattern.compile("<a .+>《(.{6,100})》</a>");

    private cn.meteor.beyondclouds.modules.message.service.IMessageService messageService;

    private cn.meteor.beyondclouds.modules.user.service.IUserFollowService userFollowService;

    private cn.meteor.beyondclouds.modules.user.service.IUserService userService;

    private cn.meteor.beyondclouds.modules.blog.service.IBlogService blogService;

    private cn.meteor.beyondclouds.modules.blog.service.IBlogCommentService blogCommentService;

    private cn.meteor.beyondclouds.modules.project.service.IProjectService projectService;

    private cn.meteor.beyondclouds.modules.project.service.IProjectCommentService projectCommentService;

    private cn.meteor.beyondclouds.modules.post.service.IPostService postService;

    private cn.meteor.beyondclouds.modules.post.service.IPostCommentService postCommentService;

    private cn.meteor.beyondclouds.modules.question.service.IQuestionReplyService questionReplyService;

    private cn.meteor.beyondclouds.modules.question.service.IQuestionService questionService;

    private cn.meteor.beyondclouds.modules.im.server.SocketIOService socketIOService;

    private cn.meteor.beyondclouds.modules.blog.service.IBlogPraiseService blogPraiseService;

    private cn.meteor.beyondclouds.modules.post.service.IPostPraiseService postPraiseService;

    private cn.meteor.beyondclouds.modules.question.service.IQuestionPraiseService questionPraiseService;

    private cn.meteor.beyondclouds.modules.project.service.IProjectPraiseService projectPraiseService;

    private cn.meteor.beyondclouds.core.redis.TokenManager tokenManager;

    @org.springframework.beans.factory.annotation.Autowired
    public void setTokenManager(cn.meteor.beyondclouds.core.redis.TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setPostPraiseService(cn.meteor.beyondclouds.modules.post.service.IPostPraiseService postPraiseService) {
        this.postPraiseService = postPraiseService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setBlogPraiseService(cn.meteor.beyondclouds.modules.blog.service.IBlogPraiseService blogPraiseService) {
        this.blogPraiseService = blogPraiseService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setProjectPraiseService(cn.meteor.beyondclouds.modules.project.service.IProjectPraiseService projectPraiseService) {
        this.projectPraiseService = projectPraiseService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setQuestionPraiseService(cn.meteor.beyondclouds.modules.question.service.IQuestionPraiseService questionPraiseService) {
        this.questionPraiseService = questionPraiseService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setMessageService(cn.meteor.beyondclouds.modules.message.service.IMessageService messageService) {
        this.messageService = messageService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setSocketIOService(cn.meteor.beyondclouds.modules.im.server.SocketIOService socketIOService) {
        this.socketIOService = socketIOService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setUserFollowService(cn.meteor.beyondclouds.modules.user.service.IUserFollowService userFollowService) {
        this.userFollowService = userFollowService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setPostService(cn.meteor.beyondclouds.modules.post.service.IPostService postService) {
        this.postService = postService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setPostCommentService(cn.meteor.beyondclouds.modules.post.service.IPostCommentService postCommentService) {
        this.postCommentService = postCommentService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setBlogService(cn.meteor.beyondclouds.modules.blog.service.IBlogService blogService) {
        this.blogService = blogService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setBlogCommentService(cn.meteor.beyondclouds.modules.blog.service.IBlogCommentService blogCommentService) {
        this.blogCommentService = blogCommentService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setProjectService(cn.meteor.beyondclouds.modules.project.service.IProjectService projectService) {
        this.projectService = projectService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setProjectCommentService(cn.meteor.beyondclouds.modules.project.service.IProjectCommentService projectCommentService) {
        this.projectCommentService = projectCommentService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setQuestionReplyService(cn.meteor.beyondclouds.modules.question.service.IQuestionReplyService questionReplyService) {
        this.questionReplyService = questionReplyService;
    }

    @java.lang.Override
    public void onUserAvatarUpdate(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) {
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setQuestionService(cn.meteor.beyondclouds.modules.question.service.IQuestionService questionService) {
        this.questionService = questionService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setUserService(cn.meteor.beyondclouds.modules.user.service.IUserService userService) {
        this.userService = userService;
    }

    @java.lang.Override
    public void onDataItemAdd(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) throws java.lang.Exception {
        cn.meteor.beyondclouds.core.queue.message.DataItemType itemType = dataItemChangeMessage.getItemType();
        java.io.Serializable itemId = dataItemChangeMessage.getItemId();
        java.lang.String operatorId = dataItemChangeMessage.getOperatorId();
        java.lang.String toUserId = null;
        java.lang.String msg = null;
        java.lang.Integer msgType = null;
        cn.meteor.beyondclouds.modules.user.entity.User fromUser = null;
        if (dataItemChangeMessage.getSubject().isAuthenticated()) {
            fromUser = userService.getById(dataItemChangeMessage.getSubject().getId());
        }
        // 关注消息
        if (itemType.equals(cn.meteor.beyondclouds.core.queue.message.DataItemType.USER_FOLLOW)) {
            cn.meteor.beyondclouds.modules.user.entity.UserFollow userFollow = userFollowService.getById(itemId);
            java.lang.String followedUserId = userFollow.getFollowedId();
            java.lang.String followerUserId = userFollow.getFollowerId();
            cn.meteor.beyondclouds.modules.user.entity.User followerUser = userService.getById(followerUserId);
            toUserId = followedUserId;
            msgType = cn.meteor.beyondclouds.modules.message.enums.MessageType.FOLLOW.getType();
            msg = java.lang.String.format("<a target='_blank' href='/u/%s/blog'>%s</a>关注了你！", followerUser.getUserId(), followerUser.getNickName());
        }
        // 博客评论
        if (itemType.equals(cn.meteor.beyondclouds.core.queue.message.DataItemType.BLOG_COMMENT)) {
            // 获取评论
            cn.meteor.beyondclouds.modules.blog.entity.BlogComment blogComment = blogCommentService.getById(itemId);
            // 获取父评论
            cn.meteor.beyondclouds.modules.blog.entity.BlogComment parentComment = null;
            if (blogComment.getParentId() != null) {
                parentComment = blogCommentService.getById(blogComment.getParentId());
            }
            // 获取博客
            cn.meteor.beyondclouds.modules.blog.entity.Blog blog = blogService.getById(blogComment.getBlogId());
            // 给博主发消息
            if (!operatorId.equals(blog.getUserId())) {
                msgType = cn.meteor.beyondclouds.modules.message.enums.MessageType.BLOG_COMMENT.getType();
                toUserId = blog.getUserId();
                msg = java.lang.String.format("<a target='_blank' href='/u/%s/blog'>%s</a>对您的博客<a target='_blank' href='/blog/detail/%s'>《%s》</a>进行了评论！", blogComment.getUserId(), blogComment.getUserNick(), blog.getBlogId(), blog.getBlogTitle());
            }
            // 给父评论的用户发消息
            if ((null != parentComment) && (!operatorId.equals(parentComment.getUserId()))) {
                msgType = cn.meteor.beyondclouds.modules.message.enums.MessageType.BLOG_COMMENT.getType();
                toUserId = parentComment.getUserId();
                msg = java.lang.String.format("<a target='_blank' href='/u/%s/blog'>%s</a>对您在博客<a target='_blank' href='/blog/detail/%s'>《%s》</a>下的评论进行了回复！", blogComment.getUserId(), blogComment.getUserNick(), blog.getBlogId(), blog.getBlogTitle());
            }
        }
        // 项目评论
        if (itemType.equals(cn.meteor.beyondclouds.core.queue.message.DataItemType.PROJECT_COMMENT)) {
            // 获取评论
            cn.meteor.beyondclouds.modules.project.entity.ProjectComment comment = projectCommentService.getById(itemId);
            // 获取父评论
            cn.meteor.beyondclouds.modules.project.entity.ProjectComment parentComment = null;
            if (comment.getParentId() != null) {
                parentComment = projectCommentService.getById(parentComment.getParentId());
            }
            // 获取博客
            cn.meteor.beyondclouds.modules.project.entity.Project project = projectService.getById(comment.getProjectId());
            // 给博主发消息
            if (!operatorId.equals(project.getUserId())) {
                msgType = cn.meteor.beyondclouds.modules.message.enums.MessageType.PROJECT_COMMENT.getType();
                toUserId = project.getUserId();
                msg = java.lang.String.format("<a target='_blank' href='/u/%s/blog'>%s</a>对您的项目<a target='_blank' href='/blog/project/%d'>《%》</a>进行了评论！", comment.getUserId(), comment.getUserNick(), project.getProjectId(), project.getProjectName());
            }
            // 给父评论的用户发消息
            if ((null != parentComment) && (!operatorId.equals(parentComment.getUserId()))) {
                msgType = cn.meteor.beyondclouds.modules.message.enums.MessageType.PROJECT_COMMENT.getType();
                toUserId = parentComment.getUserId();
                msg = java.lang.String.format("<a target='_blank' href='/u/%s/blog'>%s</a>对您在项目<a target='_blank' href='/project/detail/%d'>《%s》</a>下的评论进行了回复！", comment.getUserId(), comment.getUserNick(), project.getProjectId(), project.getProjectName());
            }
        }
        // 动态评论
        if (itemType.equals(cn.meteor.beyondclouds.core.queue.message.DataItemType.POST_COMMENT)) {
            // 获取评论
            cn.meteor.beyondclouds.modules.post.entity.PostComment comment = postCommentService.getById(itemId);
            // 获取父评论
            cn.meteor.beyondclouds.modules.post.entity.PostComment parentComment = null;
            if (comment.getParentId() != null) {
                parentComment = postCommentService.getById(parentComment.getParentId());
            }
            // 获取博客
            cn.meteor.beyondclouds.modules.post.entity.Post post = postService.getById(comment.getPostId());
            // 给博主发消息
            if (!operatorId.equals(post.getUserId())) {
                msgType = cn.meteor.beyondclouds.modules.message.enums.MessageType.POST_COMMENT.getType();
                toUserId = post.getUserId();
                msg = java.lang.String.format("<a target='_blank' href='/u/%s/blog'>%s</a>对您的动态《%s》进行了评论！", comment.getUserId(), comment.getUserNick(), post.getContent());
            }
            // 给父评论的用户发消息
            if ((null != parentComment) && (!parentComment.getUserId().equals(operatorId))) {
                msgType = cn.meteor.beyondclouds.modules.message.enums.MessageType.POST_COMMENT.getType();
                toUserId = parentComment.getUserId();
                msg = java.lang.String.format("<a target='_blank' href='/u/%s/blog'>%s</a>对您在动态《%s》下的评论进行了回复！", comment.getUserId(), comment.getUserNick(), post.getContent());
            }
        }
        // 问题
        if (itemType.equals(cn.meteor.beyondclouds.core.queue.message.DataItemType.QUESTION_REPLY)) {
            // 获取回答
            cn.meteor.beyondclouds.modules.question.entity.QuestionReply questionReply = questionReplyService.getById(itemId);
            // 获取问题
            cn.meteor.beyondclouds.modules.question.entity.Question question = questionService.getById(questionReply.getQuestionId());
            // 给问题提出者发消息
            if (!operatorId.equals(question.getUserId())) {
                msgType = cn.meteor.beyondclouds.modules.message.enums.MessageType.QUESTION_REPLY.getType();
                toUserId = question.getUserId();
                msg = java.lang.String.format("<a target='_blank' href='/u/%s/blog'>%s</a>对您的问题<a target='_blank' href='/answer/detail/%s'>《%s》</a>进行了回答！", questionReply.getUserId(), questionReply.getUserNick(), question.getQuestionId(), question.getQuestionTitle());
            }
        }
        /**
         * 博客点赞
         */
        if (itemType.equals(cn.meteor.beyondclouds.core.queue.message.DataItemType.BLOG_PRAISE)) {
            cn.meteor.beyondclouds.modules.blog.entity.BlogPraise blogPraise = blogPraiseService.getById(itemId);
            cn.meteor.beyondclouds.modules.blog.entity.Blog blog = blogService.getById(blogPraise.getTargetId());
            if (!blogPraise.getUserId().equals(blog.getUserId())) {
                msgType = cn.meteor.beyondclouds.modules.message.enums.MessageType.BLOG_PRAISE.getType();
                toUserId = blog.getUserId();
                msg = java.lang.String.format("<a target='_blank' href='/u/%s/blog'>%s</a>赞了您的博客<a target='_blank' href='/blog/detail/%s'>《%s》</a>！", fromUser.getUserId(), fromUser.getNickName(), blog.getBlogId(), blog.getBlogTitle());
            }
        }
        /**
         * 项目点赞
         */
        if (itemType.equals(cn.meteor.beyondclouds.core.queue.message.DataItemType.PROJECT_PRAISE)) {
            cn.meteor.beyondclouds.modules.project.entity.ProjectPraise projectPraise = projectPraiseService.getById(itemId);
            cn.meteor.beyondclouds.modules.project.entity.Project project = projectService.getById(projectPraise.getTargetId());
            if (!projectPraise.getUserId().equals(project.getUserId())) {
                msgType = cn.meteor.beyondclouds.modules.message.enums.MessageType.PROJECT_PRAISE.getType();
                toUserId = project.getUserId();
                msg = java.lang.String.format("<a target='_blank' href='/u/%s/blog'>%s</a>赞了您的项目<a target='_blank' href='/project/detail/%d'>《%s》</a>！", fromUser.getUserId(), fromUser.getNickName(), project.getProjectId(), project.getProjectName());
            }
        }
        /**
         * 问题点赞
         */
        if (itemType.equals(cn.meteor.beyondclouds.core.queue.message.DataItemType.QUESTION_PRAISE)) {
            cn.meteor.beyondclouds.modules.question.entity.QuestionPraise questionPraise = questionPraiseService.getById(itemId);
            cn.meteor.beyondclouds.modules.question.entity.Question question = questionService.getById(questionPraise.getTargetId());
            if (!questionPraise.getUserId().equals(question.getUserId())) {
                msgType = cn.meteor.beyondclouds.modules.message.enums.MessageType.QUESTION_PRAISE.getType();
                toUserId = question.getUserId();
                msg = java.lang.String.format("<a target='_blank' href='/u/%s/blog'>%s</a>赞了您的问答<a target='_blank' href='/answer/detail/%s'>《%s》</a>！", fromUser.getUserId(), fromUser.getNickName(), question.getQuestionId(), question.getQuestionTitle());
            }
        }
        /**
         * 动态点赞
         */
        if (itemType.equals(cn.meteor.beyondclouds.core.queue.message.DataItemType.POST_PRAISE)) {
            cn.meteor.beyondclouds.modules.post.entity.PostPraise postPraise = postPraiseService.getById(itemId);
            cn.meteor.beyondclouds.modules.post.entity.Post post = postService.getById(postPraise.getTargetId());
            if (!postPraise.getUserId().equals(post.getUserId())) {
                msgType = cn.meteor.beyondclouds.modules.message.enums.MessageType.POST_PRAISE.getType();
                toUserId = post.getUserId();
                msg = java.lang.String.format("<a target='_blank' href='/u/%s/blog'>%s</a>赞了您的动态《%s》！", fromUser.getUserId(), fromUser.getNickName(), post.getContent());
            }
        }
        if (null != toUserId) {
            saveAndSendMsg(fromUser, toUserId, msgType, msg);
        }
    }

    @java.lang.Override
    public void onDataItemUpdate(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) throws java.lang.Exception {
        cn.meteor.beyondclouds.core.queue.message.DataItemType itemType = dataItemChangeMessage.getItemType();
        java.io.Serializable itemId = dataItemChangeMessage.getItemId();
        java.lang.String operatorId = dataItemChangeMessage.getOperatorId();
        java.lang.String toUserId = null;
        java.lang.String msg = null;
        java.lang.Integer msgType = null;
        cn.meteor.beyondclouds.modules.user.entity.User fromUser = null;
        if (dataItemChangeMessage.getSubject().isAuthenticated()) {
            fromUser = userService.getById(dataItemChangeMessage.getSubject().getId());
        }
        // 回答采纳
        if (itemType.equals(cn.meteor.beyondclouds.core.queue.message.DataItemType.QUESTION_REPLY_ACCEPT)) {
            // 获取回答
            cn.meteor.beyondclouds.modules.question.entity.QuestionReply questionReply = questionReplyService.getById(itemId);
            // 获取问题
            cn.meteor.beyondclouds.modules.question.entity.Question question = questionService.getById(questionReply.getQuestionId());
            // 给回答者发消息
            if (!questionReply.getUserId().equals(operatorId)) {
                msgType = cn.meteor.beyondclouds.modules.message.enums.MessageType.QUESTION_REPLY_ACCEPTED.getType();
                toUserId = questionReply.getUserId();
                msg = java.lang.String.format("您在问题<a target='_blank' href='/answer/detail/%s'>《%s》</a>的回答被采纳了！", question.getQuestionId(), question.getQuestionTitle());
            }
        }
        if (null != toUserId) {
            saveAndSendMsg(fromUser, toUserId, msgType, msg);
        }
    }

    private void saveAndSendMsg(cn.meteor.beyondclouds.modules.user.entity.User fromUser, java.lang.String to, java.lang.Integer msgType, java.lang.String msg) {
        cn.meteor.beyondclouds.modules.message.entity.Message message = new cn.meteor.beyondclouds.modules.message.entity.Message();
        if (null != fromUser) {
            message.setFromId(fromUser.getUserId());
            message.setFromName(fromUser.getNickName());
            message.setFromAvatar(fromUser.getUserAvatar());
        } else {
            message.setFromId(cn.meteor.beyondclouds.core.constant.SysConstants.SYS_ID);
            message.setFromName(cn.meteor.beyondclouds.core.constant.SysConstants.SYS_ID);
            message.setFromName(cn.meteor.beyondclouds.core.constant.SysConstants.SYS_NAME);
            message.setFromAvatar("");
        }
        message.setMsgType(msgType);
        message.setMsgContent(msg);
        message.setToId(to);
        messageService.save(message);
        java.lang.Integer totalCount = messageService.getTotalCount(to);
        java.lang.Integer unReadCount = messageService.getUnReadCount(to);
        cn.meteor.beyondclouds.modules.message.dto.MessageDTO messageDTO = new cn.meteor.beyondclouds.modules.message.dto.MessageDTO();
        org.springframework.beans.BeanUtils.copyProperties(message, messageDTO);
        messageDTO.setTotalCount(totalCount);
        messageDTO.setUnReadCount(unReadCount);
        // 对消息长度进行收缩
        contractMsgContent(messageDTO);
        if (tokenManager.isLoginToWeb(to)) {
            socketIOService.pushMessage(to, messageDTO);
        }
    }

    private void contractMsgContent(cn.meteor.beyondclouds.modules.message.dto.MessageDTO messageDTO) {
        java.lang.String msgContent = messageDTO.getMsgContent();
        if (messageDTO.getMsgType().equals(cn.meteor.beyondclouds.modules.message.enums.MessageType.POST_COMMENT.getType())) {
            msgContent = java.lang.String.format("<a target='_blank' href='/u/%s/blog'>%s</a>评论了您的动态<a target='_blank' href='/dynamic?type=my'>查看详情</a>！", messageDTO.getFromId(), messageDTO.getFromName());
        } else if (messageDTO.getMsgType().equals(cn.meteor.beyondclouds.modules.message.enums.MessageType.POST_PRAISE.getType())) {
            msgContent = java.lang.String.format("<a target='_blank' href='/u/%s/blog'>%s</a>赞了您的动态<a target='_blank' href='/dynamic?type=my'>查看详情</a>！", messageDTO.getFromId(), messageDTO.getFromName());
        } else {
            java.util.regex.Matcher matcher = cn.meteor.beyondclouds.modules.message.listener.MessageListener.LINK_PATTERN.matcher(msgContent);
            java.lang.StringBuffer buffer = new java.lang.StringBuffer();
            while (matcher.find()) {
                java.lang.String link = matcher.group();
                java.lang.String linkContent = matcher.group(1);
                java.lang.String contractedLinkContent = linkContent.substring(0, 5) + "...";
                link = link.replace(linkContent, contractedLinkContent);
                matcher.appendReplacement(buffer, link);
            } 
            matcher.appendTail(buffer);
            msgContent = buffer.toString();
        }
        messageDTO.setMsgContent(msgContent);
    }
}