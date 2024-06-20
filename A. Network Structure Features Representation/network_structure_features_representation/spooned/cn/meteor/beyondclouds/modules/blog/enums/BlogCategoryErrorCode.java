package cn.meteor.beyondclouds.modules.blog.enums;
/**
 *
 * @author gaoTong
 * @date 2020/2/2 10:02
 */
public enum BlogCategoryErrorCode implements cn.meteor.beyondclouds.core.IErrorCode {

    INCORRECT_CATEGORY(8005, "博客分类错误");
    private long code;

    private java.lang.String msg;

    BlogCategoryErrorCode(long code, java.lang.String msg) {
        this.code = code;
        this.msg = msg;
    }

    @java.lang.Override
    public long code() {
        return code;
    }

    @java.lang.Override
    public java.lang.String msg() {
        return msg;
    }
}