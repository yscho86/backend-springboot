package cc.grum.base.backendspringboot.api.interceptor;

import cc.grum.base.backendspringboot.api.v1.base.exception.UnAuthException;
import cc.grum.base.backendspringboot.core.utils.LogUtils;
import cc.grum.base.backendspringboot.core.utils.RequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AuthApiInterceptor implements AsyncHandlerInterceptor {

    private static final String ACCEPT_LANGUAGE_HEADER = "Accept-Language";
    private static final String ACCEPT_TIMEZONE_HEADER = "Accept-Timezone";
    private static final String UUID_HEADER = "UUID";
    private static final String OS_HEADER = "OS";
    private static final String VERSION_HEADER = "Version";
    private static final String SECURITY_KEY_HEADER = "nu_security_key";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if(!hasKey(request, ACCEPT_LANGUAGE_HEADER) || !hasKey(request, UUID_HEADER) || !hasKey(request, OS_HEADER) || !hasKey(request, VERSION_HEADER) || !hasKey(request, ACCEPT_TIMEZONE_HEADER))
       {
            LogUtils.d("InvalidHeaderFormatException :::::::::::");
            throw new UnAuthException();
        }

        if(hasKey(request, SECURITY_KEY_HEADER)) {
            //TODO
        }


        return true;
    }

    // Request Header 에서 Value값 체크
    private boolean hasKey(HttpServletRequest request, String key) {

        String value = request.getHeader(key);
        return StringUtils.hasText(value) ? true : false;

    }

}
