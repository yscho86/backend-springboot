package cc.grum.base.backendspringboot.config.security.token;

import cc.grum.base.backendspringboot.api.v1.base.exception.UnAuthException;
import cc.grum.base.backendspringboot.config.security.Role;
import cc.grum.base.backendspringboot.config.security.token.AuthTokenProvider;
import cc.grum.base.backendspringboot.config.security.token.JwtAuthToken;
import cc.grum.base.backendspringboot.core.utils.LogUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtAuthTokenProvider implements AuthTokenProvider<JwtAuthToken> {

    private final Key key;
    //private static final String AUTHORITIES_KEY = "role";
    private static final String AUTHORITIES_KEY = "auth";

    public JwtAuthTokenProvider(String base64Secret) {
        byte[] keyBytes = Decoders.BASE64.decode(base64Secret);

        LogUtils.d("base64Secret: {}", base64Secret);
        LogUtils.d("keyBytes: {}", keyBytes);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        // this.key = new SecretKeySpec(keyBytes, "HmacSHA512");
        // this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    @Override
    public JwtAuthToken createAuthToken(String id, String role, Date expiredDate) {
        return new JwtAuthToken(id, role, expiredDate, key);
    }

    @Override
    public JwtAuthToken convertAuthToken(String token) {
        return new JwtAuthToken(token, key);
    }

    @Override
    public Authentication getAuthentication(JwtAuthToken authToken) {
        LogUtils.dStart();

        if(authToken.validate()) {
            Claims claims = authToken.getData();

            String key = claims.get(AUTHORITIES_KEY).toString();
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(new String[]{key})
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            UserDetails principal = new User(claims.getSubject(), "", authorities);
            return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);

        } else {
            LogUtils.d("TokenValidFailedException :::::::::::");
            throw new UnAuthException();
        }
    }

    @Override
    public Authentication getAuthentication(String subject) {
        LogUtils.dStart();

        String key = Role.GUEST.getKey();
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(new String[]{key})
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());


        UserDetails principal = new User(subject, "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, null, authorities);

    }

}
