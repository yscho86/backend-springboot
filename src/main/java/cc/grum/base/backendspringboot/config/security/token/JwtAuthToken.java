package cc.grum.base.backendspringboot.config.security.token;

import cc.grum.base.backendspringboot.config.security.token.AuthToken;
import cc.grum.base.backendspringboot.core.utils.LogUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SecurityException;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.*;

public class JwtAuthToken implements AuthToken<Claims> {

    //private static final long JWT_ACCESS_TOKEN_VALIDITY = 1 * 60; // 30분
    private static final long JWT_ACCESS_TOKEN_VALIDITY = 20; // 10초
    //private static final long JWT_REFRESH_TOKEN_VALIDITY = 24 * 60 * 60 * 7; // 일주일
    private static final long JWT_REFRESH_TOKEN_VALIDITY = 40; // 일주일
    private static final String AUTHORITIES_KEY = "auth";


    @Getter
    private final String token;
    @Getter
    private String refreshToken;
    @Getter
    private Date tokenExpiredDate;
    @Getter
    private Date refreshTokenExpiredDate;

    private final Key secretKey;

    JwtAuthToken(String token, Key secretKey) {
        this.token = token;
        this.secretKey = secretKey;
    }

    JwtAuthToken(String id, String role, Date expiredDate, Key secretKey) {
        this.secretKey = secretKey;
        //this.tokenExpiredDate = expiredDate;
        this.tokenExpiredDate = new Date((new Date()).getTime() + JWT_ACCESS_TOKEN_VALIDITY * 1000);
        this.token = createJwtAuthToken(id, role, tokenExpiredDate).get();
        this.refreshTokenExpiredDate = new Date((new Date()).getTime() + JWT_REFRESH_TOKEN_VALIDITY * 1000);
        this.refreshToken = createJwtAuthToken(id, role, refreshTokenExpiredDate).get();
    }

    @Override
    public boolean validate() {
        return getData() != null;
    }

    @Override
    public Claims getData() {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        } catch (SecurityException e) {
            LogUtils.d("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            LogUtils.d("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            LogUtils.d("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            LogUtils.d("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            LogUtils.d("JWT token compact of handler are invalid.");
        }
        return null;
    }



    private Optional<String> createJwtAuthToken(String id, String role, Date expiredDate) {

        String token = Jwts.builder()
                .setSubject(id)
                .claim(AUTHORITIES_KEY, role)
                .setIssuedAt(new Date((new Date()).getTime()))
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

        return Optional.ofNullable(token);
    }

}
