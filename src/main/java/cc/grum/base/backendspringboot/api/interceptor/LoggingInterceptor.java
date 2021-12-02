package cc.grum.base.backendspringboot.api.interceptor;

import cc.grum.base.backendspringboot.core.utils.LogUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class LoggingInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestId = UUID.randomUUID().toString().toUpperCase().replace("-", "");
        Long startTime = System.currentTimeMillis();
        request.setAttribute("request-id", requestId);
        request.setAttribute("startTime", startTime);

        LogUtils.d("[{}] request: {}", requestId, request.getRequestURI());
        LogUtils.d("[{}] method: {}", requestId, request.getMethod());
        LogUtils.d("[{}] remote-ip: {}", requestId, request.getRemoteAddr());
        LogUtils.d("[{}] remote-locale: {}", requestId, LocaleContextHolder.getLocale().getCountry());
        LogUtils.d("[{}] timezone: {}", requestId, LocaleContextHolder.getTimeZone().getID());
        LogUtils.d("[{}] user-agent: {}", requestId, request.getHeader("User-Agent"));
        LogUtils.d("[{}] referer: {}", requestId, request.getHeader("Referer"));

        Device device = DeviceUtils.getCurrentDevice(request);
        if (device != null) {
            LogUtils.d("[{}] device: {}", requestId, device.getDevicePlatform());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String requestId = (String)request.getAttribute("request-id");

        Long startTime = (Long) request.getAttribute("startTime");
        Long endTime = System.currentTimeMillis();
        Long executionTime = endTime - startTime;

        LogUtils.d("[{}] execution-time-seconds : {}", requestId, Double.valueOf(executionTime) / 1000);
        LogUtils.d("[{}] log-time : {}", requestId, LocalDateTime.now());
    }


}
