package cn.meteor.beyondclouds.core.authentication;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 对本系统访问者的抽象
 *
 * @author meteor
 */
@lombok.Data
@lombok.NoArgsConstructor
public class Subject implements java.io.Serializable {
    /**
     * 访问者唯一标识
     */
    private java.io.Serializable id;

    /**
     * 客户端类型
     */
    private cn.meteor.beyondclouds.modules.user.enums.ClientType clientType;

    /**
     * 访问者IP地址
     */
    private java.lang.String ipAddress;

    /**
     * 访问者类型
     */
    private cn.meteor.beyondclouds.core.emuns.SubjectType type;

    private Subject(java.io.Serializable id, cn.meteor.beyondclouds.modules.user.enums.ClientType clientType, cn.meteor.beyondclouds.core.emuns.SubjectType type, java.lang.String ipAddress) {
        this.id = id;
        this.type = type;
        this.ipAddress = ipAddress;
        this.clientType = clientType;
    }

    public static cn.meteor.beyondclouds.core.authentication.Subject anonymous(java.lang.String ipAddress) {
        return new cn.meteor.beyondclouds.core.authentication.Subject(null, null, cn.meteor.beyondclouds.core.emuns.SubjectType.ANONYMOUS, ipAddress);
    }

    public static cn.meteor.beyondclouds.core.authentication.Subject anonymous() {
        return new cn.meteor.beyondclouds.core.authentication.Subject(cn.meteor.beyondclouds.core.constant.SysConstants.SYS_ID, null, cn.meteor.beyondclouds.core.emuns.SubjectType.ANONYMOUS, cn.meteor.beyondclouds.core.constant.SysConstants.SYS_ID);
    }

    public static cn.meteor.beyondclouds.core.authentication.Subject authenticated(java.io.Serializable id, java.lang.Integer clientType, java.lang.String ipAddress) {
        return new cn.meteor.beyondclouds.core.authentication.Subject(id, cn.meteor.beyondclouds.modules.user.enums.ClientType.valueOf(clientType), cn.meteor.beyondclouds.core.emuns.SubjectType.AUTHENTICATED, ipAddress);
    }

    /**
     * 判断访问者是否经过认证
     *
     * @return  */
    @com.fasterxml.jackson.annotation.JsonIgnore
    public boolean isAuthenticated() {
        return type.equals(cn.meteor.beyondclouds.core.emuns.SubjectType.AUTHENTICATED);
    }

    @java.lang.Override
    public java.lang.String toString() {
        java.lang.StringBuilder builder = new java.lang.StringBuilder();
        builder.append("Subject(");
        if (isAuthenticated()) {
            builder.append("id=");
            builder.append(id);
            builder.append(", ");
        }
        builder.append("ipAddress=");
        builder.append(ipAddress);
        builder.append(", ");
        builder.append("type=");
        builder.append(type.name());
        builder.append(")");
        return builder.toString();
    }

    @com.fasterxml.jackson.annotation.JsonIgnore
    public java.lang.String getIdentification() {
        java.lang.StringBuilder builder = new java.lang.StringBuilder();
        builder.append("Subject:");
        if (isAuthenticated()) {
            builder.append(id);
            builder.append(":");
        }
        builder.append(ipAddress);
        builder.append(":");
        builder.append(type.name());
        return builder.toString();
    }
}