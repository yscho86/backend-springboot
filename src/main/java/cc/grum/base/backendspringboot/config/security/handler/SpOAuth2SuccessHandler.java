package cc.grum.base.backendspringboot.config.security.handler;

import cc.grum.base.backendspringboot.api.v1.model.request.ReqSignIn;
import cc.grum.base.backendspringboot.api.v1.service.MemberSignApiService;
import cc.grum.base.backendspringboot.config.security.spOAuth.OAuth2Provider;
import cc.grum.base.backendspringboot.config.security.token.AuthToken;
import cc.grum.base.backendspringboot.config.security.token.JwtAuthToken;
import cc.grum.base.backendspringboot.config.security.token.JwtAuthTokenProvider;
import cc.grum.base.backendspringboot.core.db.entity.MemberMaster;
import cc.grum.base.backendspringboot.core.db.entity.MemberToken;
import cc.grum.base.backendspringboot.core.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class SpOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private final JwtAuthTokenProvider jwtAuthTokenProvider;


    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Object principal = authentication.getPrincipal();
        if(principal instanceof OidcUser) {

            //Google etc...
            MemberMaster member = OAuth2Provider.Provider.google.convert((OidcUser) principal);


            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(member.getLoginId(), null);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            MemberToken memberToken = createMemberToken(member);

            // generate Token and save in redis
            RedisUtils.set(memberToken.getLoginId(), memberToken, memberToken.getRefreshTokenExpiredDate());

            //return new ResSignIn(memberToken);


        } else if (principal instanceof OAuth2User) {

            //Naver, facebook
        }


    }

    public AuthToken createAuthToken(MemberMaster member) {
        //Date expiredDate = Date.from(LocalDateTime.now().plusMinutes(LOGIN_RETENTION_MINUTES).atZone(ZoneId.systemDefault()).toInstant());
        Date expiredDate = null;
        return jwtAuthTokenProvider.createAuthToken(member.getUsername(), member.getRole(), expiredDate);
    }

    public MemberToken createMemberToken(MemberMaster member) {

        JwtAuthToken authToken = (JwtAuthToken)createAuthToken(member);

        MemberToken memberToken = new MemberToken(member);
        memberToken.setAccessToken(authToken.getToken());
        memberToken.setAccessTokenExpiredDate(authToken.getTokenExpiredDate().getTime());
        memberToken.setRefreshToken(authToken.getRefreshToken());
        memberToken.setRefreshTokenExpiredDate(authToken.getRefreshTokenExpiredDate().getTime());

        return memberToken;

    }


}
