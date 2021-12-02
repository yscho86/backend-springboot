package cc.grum.base.backendspringboot.config.security;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Role {
    ADMIN("ADMIN", "관리자권한"),
    MEMBER("MEMBER", "사용자권한"),
    GUEST("GUEST", "손님권한");

    private String key;
    private String title;

    Role(String key, String title) {
        this.key = key;
        this.title = title;
    }

    public static Role of(String key) {
        return Arrays.stream(Role.values())
                .filter(r -> r.getKey().equals(key))
                .findAny()
                .orElse(GUEST);
    }
}
