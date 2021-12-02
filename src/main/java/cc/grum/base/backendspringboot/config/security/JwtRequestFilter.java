package cc.grum.base.backendspringboot.config.security;

import cc.grum.base.backendspringboot.api.v1.base.exception.UnAuthException;
import cc.grum.base.backendspringboot.config.security.token.JwtAuthToken;
import cc.grum.base.backendspringboot.config.security.token.JwtAuthTokenProvider;
import cc.grum.base.backendspringboot.core.utils.LogUtils;
import cc.grum.base.backendspringboot.core.utils.RedisUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;


public class JwtRequestFilter extends GenericFilterBean {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String UUID_HEADER = "UUID";

    private JwtAuthTokenProvider jwtAuthTokenProvider;


    JwtRequestFilter(JwtAuthTokenProvider jwtAuthTokenProvider) {
        this.jwtAuthTokenProvider = jwtAuthTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException, UnAuthException {
        LogUtils.dStart();
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        Optional<String> token = resolveToken(httpServletRequest);

        if (token.isPresent() ) {
            LogUtils.d("http-request-token: {}", token.get());
            try {
                String isLogout = (String) RedisUtils.get(token.get());
                JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(token.get());
                Authentication authentication = jwtAuthTokenProvider.getAuthentication(jwtAuthToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
            }
        } else {

            String value = httpServletRequest.getHeader(UUID_HEADER);
            Authentication authentication = jwtAuthTokenProvider.getAuthentication(value);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    // Request Header 에서 토큰 정보를 꺼내오기
    private Optional<String> resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken ) && bearerToken.startsWith(BEARER_PREFIX)) {
            return Optional.of(bearerToken.substring(7));
        }
        return Optional.empty();
    }
}
