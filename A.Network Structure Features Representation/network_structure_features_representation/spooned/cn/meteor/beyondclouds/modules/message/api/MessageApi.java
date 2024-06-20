package cn.meteor.beyondclouds.modules.message.api;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 段启岩
 * @since 2020-02-12
 */
@cn.meteor.beyondclouds.modules.message.api.RestController
@io.swagger.annotations.Api(tags = "消息API")
@cn.meteor.beyondclouds.modules.message.api.RequestMapping("/api")
public class MessageApi {
    private cn.meteor.beyondclouds.modules.message.service.IMessageService messageService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setMessageService(cn.meteor.beyondclouds.modules.message.service.IMessageService messageService) {
        this.messageService = messageService;
    }

    @io.swagger.annotations.ApiOperation("标记消息为已读")
    @cn.meteor.beyondclouds.modules.message.api.PutMapping("/message/{messageId}/read")
    public cn.meteor.beyondclouds.core.api.Response readMessage(@cn.meteor.beyondclouds.modules.message.api.PathVariable(name = "messageId")
    java.lang.String messageId) throws cn.meteor.beyondclouds.core.exception.ServiceException {
        messageService.readMessage(messageId);
        return cn.meteor.beyondclouds.core.api.Response.success();
    }

    @io.swagger.annotations.ApiOperation("标记所有信息为已读")
    @cn.meteor.beyondclouds.modules.message.api.PutMapping("/messages/read")
    public cn.meteor.beyondclouds.core.api.Response readMessages(@cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        messageService.readMessages(java.lang.String.valueOf(subject.getId()));
        return cn.meteor.beyondclouds.core.api.Response.success();
    }

    @io.swagger.annotations.ApiOperation("我的消息列表")
    @cn.meteor.beyondclouds.modules.message.api.GetMapping("/my/messages")
    public cn.meteor.beyondclouds.core.api.Response<?> getMyMessages(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, org.springframework.validation.BindingResult bindingResult, @cn.meteor.beyondclouds.modules.message.api.RequestParam(value = "type", required = false)
    java.lang.String type) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        // 根据用户获取列表并返回
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.message.entity.Message> messageIPage = messageService.getMessagePage(pageForm.getPage(), pageForm.getSize(), ((java.lang.String) (cn.meteor.beyondclouds.util.SubjectUtils.getSubject().getId())), type);
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.message.entity.Message> messagePageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(messageIPage);
        return cn.meteor.beyondclouds.core.api.Response.success(messagePageDTO);
    }

    @io.swagger.annotations.ApiOperation("消息详情")
    @cn.meteor.beyondclouds.modules.message.api.GetMapping("/message/{messageId}")
    public cn.meteor.beyondclouds.core.api.Response getTopic(@cn.meteor.beyondclouds.modules.message.api.PathVariable("messageId")
    java.lang.String messageId) {
        try {
            cn.meteor.beyondclouds.modules.message.entity.Message message = messageService.getMessage(messageId);
            return cn.meteor.beyondclouds.core.api.Response.success(message);
        } catch (cn.meteor.beyondclouds.modules.message.exception.MessageServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("删除所有消息")
    @cn.meteor.beyondclouds.modules.message.api.DeleteMapping("/messages")
    public cn.meteor.beyondclouds.core.api.Response delAllMessage(@cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        messageService.deleteAllMessage(((java.lang.String) (subject.getId())));
        return cn.meteor.beyondclouds.core.api.Response.success();
    }

    @io.swagger.annotations.ApiOperation("删除消息")
    @cn.meteor.beyondclouds.modules.message.api.DeleteMapping("/message/{messageId}")
    public cn.meteor.beyondclouds.core.api.Response delMessage(@cn.meteor.beyondclouds.modules.message.api.PathVariable("messageId")
    java.lang.String messageId, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        try {
            messageService.deleteMessage(messageId, ((java.lang.String) (subject.getId())));
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.message.exception.MessageServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    /**
     * 获取消息数量
     *
     * @return  */
    @io.swagger.annotations.ApiOperation("获取消息数量，包括已读和未读")
    @cn.meteor.beyondclouds.modules.message.api.GetMapping("/message/count")
    public cn.meteor.beyondclouds.core.api.Response getTotalCount() {
        cn.meteor.beyondclouds.core.authentication.Subject subject = cn.meteor.beyondclouds.util.SubjectUtils.getSubject();
        java.lang.Integer totalCount = messageService.getTotalCount(((java.lang.String) (subject.getId())));
        java.lang.Integer unReadCount = messageService.getUnReadCount(((java.lang.String) (subject.getId())));
        return cn.meteor.beyondclouds.core.api.Response.success(java.util.Map.of("totalCount", totalCount, "unReadCount", unReadCount));
    }
}