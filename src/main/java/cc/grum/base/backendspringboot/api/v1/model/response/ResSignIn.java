package cc.grum.base.backendspringboot.api.v1.model.response;

import cc.grum.base.backendspringboot.core.db.entity.MemberToken;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ResSignIn {

    private String loginId;
    private String username;
    private String accessToken;
    private String refreshToken;
    private Long tokenExpiredDate;
    private Long refreshTokenExpiredDate;
    private String securityToken;

    public ResSignIn(MemberToken memberToken) {
        this.loginId = memberToken.getLoginId() ;
        this.username = memberToken.getUsername();
        this.accessToken = memberToken.getAccessToken();
        this.tokenExpiredDate = memberToken.getAccessTokenExpiredDate();
        this.refreshToken = memberToken.getRefreshToken();
        this.refreshTokenExpiredDate = memberToken.getRefreshTokenExpiredDate();
        this.securityToken = memberToken.getSecurityToken();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
