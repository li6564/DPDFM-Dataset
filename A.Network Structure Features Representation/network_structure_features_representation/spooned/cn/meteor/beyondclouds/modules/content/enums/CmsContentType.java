package cn.meteor.beyondclouds.modules.content.enums;
/**
 * 内容类型
 *
 * @author meteor
 */
public enum CmsContentType {

    /**
     * 幻灯
     */
    SLIDE_SHOW,
    /**
     * 普通文章
     */
    GENERAL_ARTICLE,
    /**
     * 广告
     */
    ADVERTISEMENT;
    public static cn.meteor.beyondclouds.modules.content.enums.CmsContentType valueOf(int ordinal) {
        for (cn.meteor.beyondclouds.modules.content.enums.CmsContentType contentType : cn.meteor.beyondclouds.modules.content.enums.CmsContentType.values()) {
            if (contentType.ordinal() == ordinal) {
                return contentType;
            }
        }
        return null;
    }
}