package cc.grum.base.backendspringboot.core.db.entity;

import cc.grum.base.backendspringboot.config.security.spOAuth.OAuth2Provider;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Alias("MemberMaster")
public class MemberMaster {
    @Id
    private Long memPid;

    private String loginId;
    private String username;
    private String password;
    private String signupSns;
    private String nickname;
    private String mobileNumber;
    private String email;
    private String emailAuthStatus;
    private String emailAuthDt;
    private String role;
    private String memStatus;
    private String createdDt;
    private String memberType;
    private String securityToken;

    private OAuth2Provider.Provider provider;

}
