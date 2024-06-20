package cn.meteor.beyondclouds.modules.topic.api;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author 胡明森
 * @since 2020/1/28
 */
@io.swagger.annotations.Api(tags = "话题Api")
@cn.meteor.beyondclouds.modules.topic.api.RestController
@cn.meteor.beyondclouds.modules.topic.api.RequestMapping("/api")
public class TopicApi {
    private cn.meteor.beyondclouds.modules.topic.service.ITopicService topicService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setTopicService(cn.meteor.beyondclouds.modules.topic.service.ITopicService topicService) {
        this.topicService = topicService;
    }

    @io.swagger.annotations.ApiOperation("创建话题")
    @cn.meteor.beyondclouds.modules.topic.api.PostMapping("/topic")
    public cn.meteor.beyondclouds.core.api.Response createTopic(@cn.meteor.beyondclouds.modules.topic.api.RequestBody
    @javax.validation.Valid
    cn.meteor.beyondclouds.modules.topic.form.CreateTopicForm createTopicForm, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        try {
            cn.meteor.beyondclouds.modules.topic.dto.TopicDTO topic = topicService.createTopic(java.lang.String.valueOf(subject.getId()), createTopicForm.getTopicName());
            return cn.meteor.beyondclouds.core.api.Response.success(topic);
        } catch (cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("话题列表")
    @cn.meteor.beyondclouds.modules.topic.api.GetMapping("/topics")
    public cn.meteor.beyondclouds.core.api.Response topics(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, org.springframework.validation.BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        int pageNo = pageForm.getPage();
        int pageSize = pageForm.getSize();
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.topic.dto.TopicDTO> topicPage = topicService.getTopicPage(pageNo, pageSize);
        return cn.meteor.beyondclouds.core.api.Response.success(topicPage);
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("检索话题")
    @cn.meteor.beyondclouds.modules.topic.api.GetMapping("/topic/search")
    public cn.meteor.beyondclouds.core.api.Response<?> searchTopics(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, org.springframework.validation.BindingResult bindingResult, @cn.meteor.beyondclouds.modules.topic.api.RequestParam("keywords")
    java.lang.String keywords, @cn.meteor.beyondclouds.core.flow.CollectAccessInfo(transmitType = cn.meteor.beyondclouds.core.flow.TransmitType.PARAM, type = cn.meteor.beyondclouds.core.flow.ParamType.SEARCH_KEYWORDS_TOPIC, paramName = "keywords")
    cn.meteor.beyondclouds.core.flow.AccessInfo accessInfo) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        boolean updateDegree = false;
        if (null != accessInfo) {
            updateDegree = accessInfo.getFieldVisitCount() < 2;
        }
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.topic.dto.TopicDTO> topicPage = topicService.searchTopics(keywords, pageForm.getPage(), pageForm.getSize(), updateDegree);
        return cn.meteor.beyondclouds.core.api.Response.success(topicPage);
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("话题详情")
    @cn.meteor.beyondclouds.modules.topic.api.GetMapping("/topic/{identification:.+}")
    public cn.meteor.beyondclouds.core.api.Response getTopic(@cn.meteor.beyondclouds.modules.topic.api.PathVariable("identification")
    java.lang.String identification, @cn.meteor.beyondclouds.modules.topic.api.RequestParam(value = "by", required = false)
    java.lang.String by) {
        cn.meteor.beyondclouds.modules.topic.enums.TopicAccessWay topicAccessWay;
        if ((null == by) || cn.meteor.beyondclouds.modules.topic.enums.TopicAccessWay.ID.getBy().equals(by)) {
            topicAccessWay = cn.meteor.beyondclouds.modules.topic.enums.TopicAccessWay.ID;
        } else {
            topicAccessWay = cn.meteor.beyondclouds.modules.topic.enums.TopicAccessWay.NAME;
        }
        try {
            cn.meteor.beyondclouds.modules.topic.dto.TopicDTO topic = topicService.getTopic(identification, topicAccessWay);
            return cn.meteor.beyondclouds.core.api.Response.success(topic);
        } catch (cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("关注话题")
    @cn.meteor.beyondclouds.modules.topic.api.PostMapping("/topic/{topicId}/follower")
    public cn.meteor.beyondclouds.core.api.Response topicFollower(@cn.meteor.beyondclouds.modules.topic.api.PathVariable("topicId")
    java.lang.String topicId, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        try {
            topicService.followTopic(((java.lang.String) (subject.getId())), topicId);
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("话题关注者列表")
    @cn.meteor.beyondclouds.modules.topic.api.GetMapping("/topic/{topicId}/follower")
    public cn.meteor.beyondclouds.core.api.Response<?> topicsFollower(@cn.meteor.beyondclouds.modules.topic.api.PathVariable("topicId")
    java.lang.String topicId, @javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, org.springframework.validation.BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> page;
        try {
            page = topicService.getTopicsFollower(pageForm.getPage(), pageForm.getSize(), topicId);
            return cn.meteor.beyondclouds.core.api.Response.success(page);
        } catch (cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException e) {
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("我关注的话题")
    @cn.meteor.beyondclouds.modules.topic.api.GetMapping("/my/topic/followed")
    public cn.meteor.beyondclouds.core.api.Response topicsMyFollowed(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, org.springframework.validation.BindingResult bindingResult, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.topic.entity.Topic> page = topicService.getTopicsMyFollowed(pageForm.getPage(), pageForm.getSize(), java.lang.String.valueOf(subject.getId()));
        return cn.meteor.beyondclouds.core.api.Response.success(page);
    }

    /**
     * 我创建的话题列表
     *
     * @param pageForm
     * @return  */
    @io.swagger.annotations.ApiOperation("我的话题列表")
    @cn.meteor.beyondclouds.modules.topic.api.GetMapping("/my/topic/created")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.topic.entity.Topic>> getMyTopics(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        // 根据用户获取列表并返回
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.topic.entity.Topic> topicPage = topicService.getTopicPage(pageForm.getPage(), pageForm.getSize(), ((java.lang.String) (subject.getId())));
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.topic.entity.Topic> topicPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(topicPage);
        return cn.meteor.beyondclouds.core.api.Response.success(topicPageDTO);
    }

    @io.swagger.annotations.ApiOperation("取消关注话题")
    @cn.meteor.beyondclouds.modules.topic.api.DeleteMapping("/topic/{topicId}/follower")
    public cn.meteor.beyondclouds.core.api.Response delTopicFollower(@cn.meteor.beyondclouds.modules.topic.api.PathVariable("topicId")
    java.lang.String topicId, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        try {
            topicService.delFollowTopic(((java.lang.String) (subject.getId())), topicId);
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("热门话题")
    @cn.meteor.beyondclouds.modules.topic.api.GetMapping("/topic/hots")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.topic.dto.TopicDTO>> getHotTags(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm) {
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.topic.dto.TopicDTO> topicPage = topicService.getHotPage(pageForm.getPage(), pageForm.getSize());
        return cn.meteor.beyondclouds.core.api.Response.success(topicPage);
    }

    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("查询话题下的所有动态")
    @cn.meteor.beyondclouds.modules.topic.api.GetMapping("/topic/{topicName}/posts")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.post.dto.PostDTO>> getPostByTopicName(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.modules.topic.api.PathVariable("topicName")
    java.lang.String topicName, @cn.meteor.beyondclouds.modules.topic.api.RequestParam(value = "type", required = false)
    java.lang.Integer type, @cn.meteor.beyondclouds.modules.topic.api.RequestParam(value = "keywords", required = false)
    java.lang.String keywords) {
        try {
            cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.post.dto.PostDTO> postPage = topicService.getPostByTopicName(pageForm.getPage(), pageForm.getSize(), topicName, type, keywords);
            return cn.meteor.beyondclouds.core.api.Response.success(postPage);
        } catch (cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error();
        }
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("热搜话题")
    @cn.meteor.beyondclouds.modules.topic.api.GetMapping("/topic/hotSearch")
    public cn.meteor.beyondclouds.core.api.Response<?> hotSearchTopics(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, org.springframework.validation.BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.topic.dto.TopicDTO> topicDTOPage = topicService.getHotSearchTopics(pageForm.getPage(), pageForm.getSize());
        return cn.meteor.beyondclouds.core.api.Response.success(topicDTOPage);
    }

    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("话题贡献者列表")
    @cn.meteor.beyondclouds.modules.topic.api.GetMapping("/topic/{topicId}/contributes")
    public cn.meteor.beyondclouds.core.api.Response<?> topicContributes(@cn.meteor.beyondclouds.modules.topic.api.PathVariable("topicId")
    java.lang.String topicId, @javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, org.springframework.validation.BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> userFollowDTOPage = topicService.getTopicContributes(topicId, pageForm.getPage(), pageForm.getSize());
        return cn.meteor.beyondclouds.core.api.Response.success(userFollowDTOPage);
    }
}