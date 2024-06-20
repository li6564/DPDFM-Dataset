package cn.meteor.beyondclouds.modules.search.entity;
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
 * @since 2020-02-13
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "SearchDegree对象", description = "")
public class SearchDegree implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId(value = "search_recore_id", type = com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID)
    private java.lang.String searchRecoreId;

    @io.swagger.annotations.ApiModelProperty("数据主键")
    private java.lang.String itemId;

    @io.swagger.annotations.ApiModelProperty("数据类型")
    private java.lang.String itemType;

    @io.swagger.annotations.ApiModelProperty("热度")
    private java.lang.Double degree;

    private java.time.LocalDateTime createTime;

    private java.time.LocalDateTime updateTime;
}