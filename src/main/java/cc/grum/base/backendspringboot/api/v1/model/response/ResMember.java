package cc.grum.base.backendspringboot.api.v1.model.response;

import cc.grum.base.backendspringboot.core.db.entity.MemberMaster;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ResMember {

    private String loginId;
    private String username;
    private String nickname;
    private String emailAuthStatus;
    private String createdDt;

    public ResMember(MemberMaster memberMaster) {
        this.loginId = memberMaster.getLoginId();
        this.username = memberMaster.getUsername();
        this.nickname = memberMaster.getNickname();
        this.emailAuthStatus = memberMaster.getEmailAuthStatus();
        this.createdDt = memberMaster.getCreatedDt();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
