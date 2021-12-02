package cc.grum.base.backendspringboot.core.db.condition;

import lombok.Builder;

@Builder
public class SignInCondition {
    private String loginId;
    private String password;
}
