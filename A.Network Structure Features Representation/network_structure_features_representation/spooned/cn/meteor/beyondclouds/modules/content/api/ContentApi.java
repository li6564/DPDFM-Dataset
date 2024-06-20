package cn.meteor.beyondclouds.modules.content.api;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
/**
 * CMS 内容
 *
 * @author 段启岩
 */
@io.swagger.annotations.Api(tags = "CMS内容API")
@cn.meteor.beyondclouds.modules.content.api.RestController
@cn.meteor.beyondclouds.modules.content.api.RequestMapping("/api")
public class ContentApi {
    private cn.meteor.beyondclouds.modules.content.service.IContentService contentService;

    @org.springframework.beans.factory.annotation.Autowired
    public ContentApi(cn.meteor.beyondclouds.modules.content.service.IContentService contentService) {
        this.contentService = contentService;
    }

    /**
     * 分页获取栏目下的内容列表
     *
     * @param channelId
     * @param pageForm
     * @param bindingResult
     * @return  */
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("内容列表")
    @cn.meteor.beyondclouds.modules.content.api.GetMapping("/channel/{channelId}/contents")
    public cn.meteor.beyondclouds.core.api.Response getContentPage(@cn.meteor.beyondclouds.modules.content.api.PathVariable("channelId")
    java.lang.Integer channelId, @cn.meteor.beyondclouds.modules.content.api.RequestParam("type")
    java.lang.Integer contentType, @javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, org.springframework.validation.BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        try {
            com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.content.entity.Content> contentPage = contentService.getPageByChannelId(channelId, contentType, pageForm.getPage(), pageForm.getSize());
            cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.content.entity.Content> contentPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(contentPage);
            return cn.meteor.beyondclouds.core.api.Response.success(contentPageDTO);
        } catch (cn.meteor.beyondclouds.modules.content.exception.ContentServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("内容详情")
    @cn.meteor.beyondclouds.modules.content.api.GetMapping("/content/{contentId}")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.modules.content.dto.ContentDetailDTO> getContentDetail(@cn.meteor.beyondclouds.modules.content.api.PathVariable("contentId")
    java.lang.Integer contentId) {
        try {
            cn.meteor.beyondclouds.modules.content.dto.ContentDetailDTO contentDetail = contentService.getContentDetail(contentId);
            return cn.meteor.beyondclouds.core.api.Response.success(contentDetail);
        } catch (cn.meteor.beyondclouds.modules.content.exception.ContentServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }
}