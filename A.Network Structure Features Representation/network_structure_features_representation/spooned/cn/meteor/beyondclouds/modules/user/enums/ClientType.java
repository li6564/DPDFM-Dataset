package cn.meteor.beyondclouds.modules.user.enums;
import io.swagger.models.auth.In;
/**
 *
 * @author meteor
 */
public enum ClientType {

    WEB(1),
    ANDROID(2);
    private java.lang.Integer type;

    ClientType(java.lang.Integer type) {
        this.type = type;
    }

    public java.lang.Integer getType() {
        return type;
    }

    public static cn.meteor.beyondclouds.modules.user.enums.ClientType valueOf(java.lang.Integer type) {
        if (null == type) {
            return cn.meteor.beyondclouds.modules.user.enums.ClientType.WEB;
        }
        for (cn.meteor.beyondclouds.modules.user.enums.ClientType clientType : cn.meteor.beyondclouds.modules.user.enums.ClientType.values()) {
            if (clientType.getType().equals(type)) {
                return clientType;
            }
        }
        return cn.meteor.beyondclouds.modules.user.enums.ClientType.WEB;
    }
}