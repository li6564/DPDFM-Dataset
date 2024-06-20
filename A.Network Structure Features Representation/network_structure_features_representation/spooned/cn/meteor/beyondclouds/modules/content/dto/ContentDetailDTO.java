package cn.meteor.beyondclouds.modules.content.dto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 *
 * @author meteor
内容详情
 */
@lombok.Data
public class ContentDetailDTO extends cn.meteor.beyondclouds.modules.content.entity.Content {
    @io.swagger.annotations.ApiModelProperty("内容")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    private java.lang.String content;
}