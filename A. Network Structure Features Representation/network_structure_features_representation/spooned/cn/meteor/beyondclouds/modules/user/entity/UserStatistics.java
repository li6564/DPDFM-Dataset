package cn.meteor.beyondclouds.modules.user.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 *
 * </p>
 *
 * @author 段启岩
 * @since 2020-02-11
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "UserStatistics对象", description = "用户统计信息表")
public class UserStatistics implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @io.swagger.annotations.ApiModelProperty("用户ID")
    @com.baomidou.mybatisplus.annotation.TableId(value = "user_id", type = com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID)
    private java.lang.String userId;

    @io.swagger.annotations.ApiModelProperty("关注数量")
    private java.lang.Integer followedNum;

    @io.swagger.annotations.ApiModelProperty("粉丝数量")
    private java.lang.Integer fansNum;

    @io.swagger.annotations.ApiModelProperty("博客数量")
    private java.lang.Integer blogNum;

    @io.swagger.annotations.ApiModelProperty("博客浏览量")
    private java.lang.Integer blogViewNum;

    @io.swagger.annotations.ApiModelProperty("项目数量")
    private java.lang.Integer projectNum;

    @io.swagger.annotations.ApiModelProperty("动态数量")
    private java.lang.Integer postNum;

    @io.swagger.annotations.ApiModelProperty("问答数量")
    private java.lang.Integer questionNum;

    @io.swagger.annotations.ApiModelProperty("回答数量")
    private java.lang.Integer questionReplyNum;

    @io.swagger.annotations.ApiModelProperty("回答被采纳数量")
    private java.lang.Integer replyAcceptedNum;

    @io.swagger.annotations.ApiModelProperty("用户被访问的次数")
    private java.lang.Integer visitedNum;

    private java.util.Date createTime;

    private java.util.Date updateTime;
}