package cn.meteor.beyondclouds.modules.post.form;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
/**
 *
 * @author gaoTong
 * @date 2020/2/2 15:25
 */
@lombok.Data
public class PostForm {
    @cn.meteor.beyondclouds.core.validation.constraints.NullOrNotBlank(message = "请输入有效的动态内容")
    private java.lang.String content;

    @javax.validation.constraints.Size(min = 1, message = "最少传入一张图片")
    @cn.meteor.beyondclouds.core.validation.constraints.ElementNotBlank(message = "图片内容不可为空")
    private java.util.List<java.lang.String> pictures;

    @cn.meteor.beyondclouds.core.validation.constraints.NullOrNotBlank(message = "请传入有效的视频内容")
    private java.lang.String video;
}