package cn.meteor.beyondclouds.core.emuns;
/**
 * 授权错误码
 *
 * @author meteor
 */
public enum AuthorizationErrorCode implements cn.meteor.beyondclouds.core.IErrorCode {

    /**
     * 没有携带Authorization请求头
     */
    NON_HEADER_AUTHORIZATION(1000, "未找到认证信息"),
    /**
     * Authorization请求头不合法
     */
    ILLEGAL_HEADER_AUTHORIZATION(1001, "非法的认证信息"),
    /**
     * 在请求头Authorization中没有携带token
     */
    NON_ACCESS_TOKEN(1002, "未携带token"),
    /**
     * token验证失败
     */
    SIGN_VERIFY_FAILURE(1003, "token验证失败"),
    /**
     * token验证失败
     */
    REFRESH_TOKEN_VERIFY_FAILURE(1004, "refresh_token验证失败");
    private long code;

    private java.lang.String msg;

    AuthorizationErrorCode(long code, java.lang.String msg) {
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