package cc.grum.base.backendspringboot.config.security;

import cc.grum.base.backendspringboot.api.v1.exception.MemberNotFoundException;
import cc.grum.base.backendspringboot.core.db.entity.MemberMaster;
import cc.grum.base.backendspringboot.core.db.mapper.MemberMapper;
import cc.grum.base.backendspringboot.core.utils.LogUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        LogUtils.d("loginId: {}", loginId);
        MemberMaster member = memberMapper.findByLoginId(loginId).orElseThrow(MemberNotFoundException::new);
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(member.getRole()));
        LogUtils.d("add role: {}", member.getRole());

        return new User(member.getUsername(), member.getPassword(), roles);
    }

}
