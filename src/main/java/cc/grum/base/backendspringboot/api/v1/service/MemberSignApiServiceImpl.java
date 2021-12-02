package cc.grum.base.backendspringboot.api.v1.service;

import cc.grum.base.backendspringboot.api.v1.base.exception.UnAuthException;
import cc.grum.base.backendspringboot.api.v1.exception.MemberNotFoundException;
import cc.grum.base.backendspringboot.api.v1.model.request.ReqSignIn;
import cc.grum.base.backendspringboot.api.v1.model.response.ResMember;
import cc.grum.base.backendspringboot.api.v1.model.response.ResSignIn;
import cc.grum.base.backendspringboot.config.security.token.JwtAuthToken;
import cc.grum.base.backendspringboot.config.security.token.JwtAuthTokenProvider;
import cc.grum.base.backendspringboot.core.db.entity.MemberMaster;
import cc.grum.base.backendspringboot.core.db.entity.MemberToken;
import cc.grum.base.backendspringboot.core.db.mapper.MemberMapper;
import cc.grum.base.backendspringboot.config.security.token.AuthToken;
import cc.grum.base.backendspringboot.core.utils.LogUtils;
import cc.grum.base.backendspringboot.core.utils.RedisUtils;
import cc.grum.base.backendspringboot.core.utils.RequestUtils;
import cc.grum.base.backendspringboot.core.utils.SHA256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class MemberSignApiServiceImpl implements MemberSignApiService {

    private final MemberMapper memberMapper;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;

    private final JwtAuthTokenProvider jwtAuthTokenProvider;
    private final static long LOGIN_RETENTION_MINUTES = 30;

    @Override
    public ResMember getById(Long pid) throws Exception {
        MemberMaster member = memberMapper.findById(pid).orElseThrow(MemberNotFoundException::new);
        return new ResMember(member);
    }

    @Override
    public void autoLogin(String loginId, String password) {
    }

    public ResSignIn signIn(ReqSignIn reqSignIn) throws Exception {
        String loginId = reqSignIn.getLoginId();
        String password = reqSignIn.getPassword();

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginId, password);

        try {
            // ID, PWD 체크 loadUserByUsername(), 실패시 Exception
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // 로그인 성공하면 인증 객체 생성 및 스프링 시큐리티 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (BadCredentialsException e) {
            throw new MemberNotFoundException();
        }

        MemberMaster member = memberMapper.findByLoginId(loginId).orElseThrow(MemberNotFoundException::new);
        MemberToken memberToken = createMemberToken(member);

        // generate Token and save in redis
        RedisUtils.set(loginId, memberToken, memberToken.getRefreshTokenExpiredDate());

        return new ResSignIn(memberToken);
    }

    @Override
    public ResSignIn refresh(MemberToken memerToken) throws Exception {

        String token = memerToken.getRefreshToken();
        JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(token);

        if(jwtAuthToken.validate()){

            String loginId = memerToken.getLoginId();
            MemberMaster member = memberMapper.findByLoginId(loginId).orElseThrow(MemberNotFoundException::new);
            MemberToken memberToken = createMemberToken(member);

            // generate Token and save in redis
            RedisUtils.set(loginId, memberToken, memberToken.getRefreshTokenExpiredDate());

            return new ResSignIn(memberToken);

        } else {
            LogUtils.d("TokenValidFailedException :::::::::::");
            throw new UnAuthException();
        }

    }

    public ResSignIn accessKey(HttpServletRequest request) throws Exception {

        MemberToken memberToken = new MemberToken();

        String uuid = request.getHeader("UUID");

        String securityToken = SHA256Util.getEncrypt(uuid, SHA256Util.generateSalt());

        memberToken.setSecurityToken(securityToken);

        MemberMaster memberMaster = new MemberMaster();
        memberMaster.setUsername(uuid);

        memberMapper.insertMember(memberMaster);


        return new ResSignIn(memberToken);

    }

    public ResSignIn signOut(ReqSignIn reqSignIn) throws Exception {
        return null;
    }


    public MemberToken createMemberToken(MemberMaster member) {

        JwtAuthToken authToken = (JwtAuthToken) jwtAuthTokenProvider.createAuthToken(member.getUsername(), member.getRole(), null);

        MemberToken memberToken = new MemberToken(member);
        memberToken.setAccessToken(authToken.getToken());
        memberToken.setAccessTokenExpiredDate(authToken.getTokenExpiredDate().getTime());
        memberToken.setRefreshToken(authToken.getRefreshToken());
        memberToken.setRefreshTokenExpiredDate(authToken.getRefreshTokenExpiredDate().getTime());

        return memberToken;

    }



    /*public AuthToken createAuthToken(MemberMaster member) {
        //Date expiredDate = Date.from(LocalDateTime.now().plusMinutes(LOGIN_RETENTION_MINUTES).atZone(ZoneId.systemDefault()).toInstant());
        Date expiredDate = null;
        return jwtAuthTokenProvider.createAuthToken(member.getUsername(), member.getRole(), expiredDate);
    }*/




}
