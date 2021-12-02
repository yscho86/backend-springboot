package cc.grum.base.backendspringboot.config.security.spOAuth;


import cc.grum.base.backendspringboot.core.db.entity.MemberMaster;
import org.springframework.security.oauth2.core.user.OAuth2User;

import static java.lang.String.format;


public class OAuth2Provider {

    public static enum Provider {
        google {
            public MemberMaster convert(OAuth2User user) {
                return MemberMaster.builder()
                        .loginId(format("%s_%s", name(), user.getAttribute("sub")))
                        .provider(google)
                        .email(user.getAttribute("email"))
                        .username(user.getAttribute("name"))
                        .build();

            }
        },
        naver {
            @Override
            public MemberMaster convert(OAuth2User userInfo) {
                return null;
            }
        },
        kakao {
            @Override
            public MemberMaster convert(OAuth2User userInfo) {
                return null;
            }
        };


        public abstract MemberMaster convert(OAuth2User userInfo);



    }

}

