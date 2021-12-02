package cc.grum.base.backendspringboot.config;

import cc.grum.base.backendspringboot.config.security.token.JwtAuthTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public JwtAuthTokenProvider jwtAuthTokenProvider() {
        return new JwtAuthTokenProvider(secret);
    }
}
