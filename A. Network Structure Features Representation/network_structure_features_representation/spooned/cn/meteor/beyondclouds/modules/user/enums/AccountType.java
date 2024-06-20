package cn.meteor.beyondclouds.modules.user.enums;
import lombok.Getter;
/**
 * 账号类型
 *
 * @author meteor
 */
@lombok.Getter
public enum AccountType {

    /**
     * 手机号
     */
    MOBILE(1, "手机号"),
    /**
     * 邮箱
     */
    EMAIL(2, "邮箱账号");
    AccountType(int type, java.lang.String name) {
        this.type = type;
        this.name = name;
    }

    private int type;

    private java.lang.String name;
}