package cc.grum.base.backendspringboot.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * 다국어 메시지 UTILs
 * @사용법
 * <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 * <spring:message code="error.common"></spring:message>
 * <spring:message code="error.minlength" arguments="테스트글자, 1"></spring:message>
 * 
 *  log.debug("error.common: "+MessageUtils.getMessage("error.common"));
 *  log.debug("error.minlength: "+MessageUtils.getMessage("error.minlength", new String[] {"테스트글자", "2"}));
 */
@Component
public class MessageUtils {
	private static MessageSourceAccessor messageSource = null;

	@Autowired
    public void setMessageSourceAccessor(@Qualifier("BusinessMessageSource")MessageSourceAccessor messageSource) {
        MessageUtils.messageSource = messageSource;
    }

    public static String getMessage(String code) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, locale);
    }
     
    public static String getMessage(String code, Object[] objs) {
        return messageSource.getMessage(code, objs);
    }
    
    public static String getMessage(String code, Object[] objs, Locale locale) {
        return messageSource.getMessage(code, objs, locale);
    }
}
