package cc.grum.base.backendspringboot.core.db.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@NoArgsConstructor
@Data
@RedisHash
public class MemberToken implements Serializable {

    private Long memPid;
    private String loginId;
    private String username;
    private String nickname;
    private String mobileNumber;
    private String role;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiredDate;
    private Long refreshTokenExpiredDate;
    private String securityToken;

    public MemberToken(MemberMaster member) {
        this.memPid = member.getMemPid() ;
        this.loginId = member.getLoginId() ;
        this.username = member.getUsername();
        this.nickname = member.getNickname() ;
        this.role = member.getRole() ;
        this.mobileNumber = member.getMobileNumber() ;
        this.securityToken = member.getSecurityToken();
    }

}
