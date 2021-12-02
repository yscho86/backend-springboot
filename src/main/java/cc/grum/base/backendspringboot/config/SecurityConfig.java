package cc.grum.base.backendspringboot.config;

import cc.grum.base.backendspringboot.config.security.JWTConfigurer;
import cc.grum.base.backendspringboot.config.security.handler.JwtAccessDeniedHandler;
import cc.grum.base.backendspringboot.config.security.handler.SpOAuth2SuccessHandler;
import cc.grum.base.backendspringboot.config.security.token.JwtAuthTokenProvider;
import cc.grum.base.backendspringboot.config.security.handler.JwtAuthenticationEntryPoint;
import cc.grum.base.backendspringboot.config.security.Role;
import cc.grum.base.backendspringboot.core.utils.LogUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthTokenProvider jwtAuthTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final SpOAuth2SuccessHandler successHandler;

    @Override
    public void configure(WebSecurity webSecurity) {
        // allow anonymous resource requests
        webSecurity.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers(
                        "/",
                        "/front/**"
                );
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        LogUtils.dStart();
        httpSecurity
                .csrf().disable()

                //.oauth2Login(oauth2->oauth2.successHandler(successHandler))

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)  // 인증실패
                .accessDeniedHandler(jwtAccessDeniedHandler)            // 인가실패



                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/sign/**").permitAll()
                .antMatchers("/api/v1/access/key").permitAll()


                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/**")
                //.hasAnyAuthority(Role.MEMBER.getKey(), Role.ADMIN.getKey())
                .hasAnyAuthority(Role.ADMIN.getKey(), Role.MEMBER.getKey(), Role.GUEST.getKey())
                .anyRequest().authenticated()

                /*.and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)*/


                .and()
                .apply(securityConfigurerAdapter());
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        LogUtils.dStart();
        return new BCryptPasswordEncoder();
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(jwtAuthTokenProvider);
    }



}
