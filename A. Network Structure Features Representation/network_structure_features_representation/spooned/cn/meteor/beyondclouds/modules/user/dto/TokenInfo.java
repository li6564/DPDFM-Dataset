package cn.meteor.beyondclouds.modules.user.dto;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *
 * @author meteor
 */
@lombok.NoArgsConstructor
@lombok.Data
public class TokenInfo {
    public TokenInfo(java.lang.String token, java.lang.String userId, java.lang.Integer clientType) {
        this.token = token;
        this.userId = userId;
        this.clientType = clientType;
    }

    private java.lang.String token;

    private java.lang.String userId;

    private java.lang.Integer clientType;

    public static cn.meteor.beyondclouds.modules.user.dto.TokenInfo create(java.lang.String token, java.lang.String userId, java.lang.Integer clientType) {
        return new cn.meteor.beyondclouds.modules.user.dto.TokenInfo(token, userId, clientType);
    }
}