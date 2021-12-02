package cc.grum.base.backendspringboot.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

@Configuration
public class MessageConfig {

    @Bean
    public ReloadableResourceBundleMessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/messages/message");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(30);  // 프로퍼티 파일의 변경을 감지할 시간 간격을 지정한다.
        // messageSource.setUseCodeAsDefaultMessage(true);	// 없는 메세지일 경우 예외를 발생시키는 대신 코드를 기본 메세지로 한다.
        messageSource.setFallbackToSystemLocale(false);
        // Locale.setDefault(Locale.US);

        return messageSource;
    }

    @Qualifier("BusinessMessageSource")
    @Bean
    public MessageSourceAccessor messageSourceAccessor() {
        return new MessageSourceAccessor(messageSource(), Locale.KOREA);
    }

}
