package cc.grum.base.backendspringboot.config;

import cc.grum.base.backendspringboot.api.interceptor.AuthApiInterceptor;
import cc.grum.base.backendspringboot.api.interceptor.LoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final AuthApiInterceptor authApiInterceptor;
    private final LoggingInterceptor loggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> includeInterceptors = Arrays.asList("/front/**");
        List<String> includeApiInterceptors = Arrays.asList("/api/**");
        List<String> includeAllInterceptors = Arrays.asList("/**");

        registry.addInterceptor(authApiInterceptor)
                .order(0)
                .addPathPatterns(includeApiInterceptors)
                .excludePathPatterns(includeInterceptors);


        registry.addInterceptor(loggingInterceptor)
                .order(1)
                .addPathPatterns(includeAllInterceptors);

    }



}
