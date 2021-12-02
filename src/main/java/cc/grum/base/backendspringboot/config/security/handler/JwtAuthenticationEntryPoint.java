package cc.grum.base.backendspringboot.config.security.handler;

import cc.grum.base.backendspringboot.api.v1.base.exception.UnAuthException;
import cc.grum.base.backendspringboot.core.utils.LogUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        LogUtils.dStart();
        LogUtils.d("request: {}", request);
        handlerExceptionResolver.resolveException(request, response, null, new UnAuthException());
    }
}
