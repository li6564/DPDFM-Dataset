package cn.meteor.beyondclouds.modules.user.dto;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *
 * @author meteor
 */
@lombok.NoArgsConstructor
@lombok.Data
public class TokenResult {
    public TokenResult(java.lang.String accessToken, java.lang.String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    private java.lang.String accessToken;

    private java.lang.String refreshToken;
}