package cn.meteor.beyondclouds.modules.user.enums;
/**
 * 用户相关错误码
 *
 * @author meteor
 */
public enum UserErrorCode implements cn.meteor.beyondclouds.core.IErrorCode {

    /**
     * 手机号已经被注册//
     */
    MOBILE_REGISTERED(13001, "该手机号已被占用"),
    EMAIL_REGISTERED(13002, "该邮箱已被占用"),
    REG_VERIFY_CODE_ERROR(13003, "验证码错误"),
    CAN_NOT_FOLLOW_SELF(13004, "不能关注自己"),
    FOLLOWED_USER_NOT_EXISTS(13005, "被关注用户不存在"),
    FOLLOWER_USER_NOT_EXISTS(13006, "关注者不存在"),
    ALREADY_FOLLOWED(13007, "已关注过该用户"),
    NON_FOLLOWED(13008, "没有关注过该用户"),
    USER_ALREADY_BLACKED(13009, "用户已经被你拉进黑名单"),
    YOU_ALREADY_BLACKED(130010, "你已经被对方拉进黑名单"),
    CAN_NOT_BLACK_SELF(130011, "不能拉黑自己"),
    USER_NOT_BLACKED(130012, "用户没有被拉黑"),
    INVALID_ACTIVE_CODE(130013, "非法的激活码"),
    BINDING_EMAIL_VERIFY_CODE_ERROR(130014, "邮箱验证码错误"),
    BINDING_MOBILE_VERIFY_CODE_ERROR(130015, "手机验证码错误"),
    NON_LOCAL_AUTH_INFO(130016, "该手机未注册"),
    USER_NOT_EXISTS(130017, "要查看的用户不存在"),
    USER_NICK_NAME_USED(130018, "该昵称已存在"),
    NICK_MODIFY_UPPER_LIMIT(130019, "本月昵称修改次数已达上限"),
    USER_DISABLED(130020, "您已被封号，请联系管理员解封"),
    EMAIL_CLOSED(130060, "邮箱注册通过暂时关闭");
    UserErrorCode(long code, java.lang.String msg) {
        this.code = code;
        this.msg = msg;
    }

    private long code;

    private java.lang.String msg;

    @java.lang.Override
    public long code() {
        return code;
    }

    @java.lang.Override
    public java.lang.String msg() {
        return msg;
    }
}