package cn.meteor.beyondclouds.modules.user.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 *
 * @program: beyond-clouds
 * @description:  * @author: Mr.Zhang
 * @create: 2020-02-12 15:32
 */
@lombok.Data
@io.swagger.annotations.ApiModel("认证信息")
public class UserAuthDTO {
    private java.lang.String authId;

    @io.swagger.annotations.ApiModelProperty("认证类型")
    private java.lang.String authType;

    @io.swagger.annotations.ApiModelProperty("账号类型")
    private int accountType;

    @io.swagger.annotations.ApiModelProperty("账号")
    private java.lang.String account;

    private java.util.Date createTime;

    private java.util.Date updateTime;
}