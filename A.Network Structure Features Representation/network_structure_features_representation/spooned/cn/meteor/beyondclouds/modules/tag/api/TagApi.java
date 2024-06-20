package cn.meteor.beyondclouds.modules.tag.api;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author 胡明森
 * @since 2020/2/2
 */
@io.swagger.annotations.Api(tags = "标签Api")
@cn.meteor.beyondclouds.modules.tag.api.RestController
@cn.meteor.beyondclouds.modules.tag.api.RequestMapping("/api")
public class TagApi {
    private cn.meteor.beyondclouds.modules.tag.service.ITagService tagService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setTagService(cn.meteor.beyondclouds.modules.tag.service.ITagService tagService) {
        this.tagService = tagService;
    }

    @io.swagger.annotations.ApiOperation("创建标签")
    @cn.meteor.beyondclouds.modules.tag.api.PostMapping("/tag")
    public cn.meteor.beyondclouds.core.api.Response createTag(@cn.meteor.beyondclouds.modules.tag.api.RequestBody
    @javax.validation.Valid
    cn.meteor.beyondclouds.modules.tag.form.CreateTagForm createTagForm, org.springframework.validation.BindingResult bindingResult, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        try {
            cn.meteor.beyondclouds.modules.tag.dto.TagDetailDTO tag = tagService.createTag(createTagForm.getTagName(), createTagForm.getTagType(), java.lang.String.valueOf(subject.getId()));
            return cn.meteor.beyondclouds.core.api.Response.success(tag);
        } catch (cn.meteor.beyondclouds.modules.tag.exception.TagServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("检索标签")
    @cn.meteor.beyondclouds.modules.tag.api.GetMapping("/tag/search")
    public cn.meteor.beyondclouds.core.api.Response<?> searchTags(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, org.springframework.validation.BindingResult bindingResult, @cn.meteor.beyondclouds.modules.tag.api.RequestParam("keywords")
    java.lang.String keywords) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.tag.entity.Tag> tagPage = tagService.searchTags(keywords, pageForm.getPage(), pageForm.getSize());
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.tag.entity.Tag> tagPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(tagPage);
        return cn.meteor.beyondclouds.core.api.Response.success(tagPageDTO);
    }

    @io.swagger.annotations.ApiOperation("我创建的标签列表")
    @cn.meteor.beyondclouds.modules.tag.api.GetMapping("/my/tag/created")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.tag.entity.Tag>> getMyTags(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        // 根据用户获取列表并返回
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.tag.entity.Tag> tag = tagService.getTagPage(pageForm.getPage(), pageForm.getSize(), ((java.lang.String) (subject.getId())));
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.tag.entity.Tag> tagPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(tag);
        return cn.meteor.beyondclouds.core.api.Response.success(tagPageDTO);
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("热门标签")
    @cn.meteor.beyondclouds.modules.tag.api.GetMapping("/tag/hots")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.tag.entity.Tag>> getHotTags(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.tag.entity.Tag> tag = tagService.getHotPage(pageForm.getPage(), pageForm.getSize());
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.tag.entity.Tag> tagPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(tag);
        return cn.meteor.beyondclouds.core.api.Response.success(tagPageDTO);
    }
}