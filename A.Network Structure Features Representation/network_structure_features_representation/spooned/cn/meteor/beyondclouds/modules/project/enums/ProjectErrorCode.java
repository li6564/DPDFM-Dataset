package cn.meteor.beyondclouds.modules.project.enums;
/**
 *
 * @author 段启岩
 */
public enum ProjectErrorCode implements cn.meteor.beyondclouds.core.IErrorCode {

    INCORRECT_CATEGORY(9001, "项目分类错误"),
    PROJECT_NOT_FOUND(9002, "找不到该项目"),
    PROJECT_DISABLED(90003, "该项目已被锁定");
    private long code;

    private java.lang.String msg;

    ProjectErrorCode(long code, java.lang.String msg) {
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