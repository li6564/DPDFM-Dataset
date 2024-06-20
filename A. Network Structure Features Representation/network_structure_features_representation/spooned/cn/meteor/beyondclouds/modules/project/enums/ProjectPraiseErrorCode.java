package cn.meteor.beyondclouds.modules.project.enums;
/**
 *
 * @author 段启岩
 */
public enum ProjectPraiseErrorCode implements cn.meteor.beyondclouds.core.IErrorCode {

    ALREADY_PRAISE(120001, "已经点过赞"),
    NON_PRAISE(120002, "没有点过赞");
    private long code;

    private java.lang.String msg;

    ProjectPraiseErrorCode(long code, java.lang.String msg) {
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