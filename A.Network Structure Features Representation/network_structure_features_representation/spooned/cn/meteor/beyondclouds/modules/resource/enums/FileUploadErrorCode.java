package cn.meteor.beyondclouds.modules.resource.enums;
/**
 *
 * @author 段启岩
 */
public enum FileUploadErrorCode implements cn.meteor.beyondclouds.core.IErrorCode {

    UPLOAD_FAILURE(2001, "文件上传失败"),
    UPLOAD_TYPE_ERROR(2002, "文件上传类型错误"),
    MAXIMUM_UPLOAD_SIZE_EXCEEDED(2003, "不能上传超过10MB的文件"),
    FILE_TOO_LARGE(2004, "文件过大!"),
    ERROR_SUFFIX(2005, "文件后缀名错误!");
    private long code;

    private java.lang.String msg;

    FileUploadErrorCode(long code, java.lang.String msg) {
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