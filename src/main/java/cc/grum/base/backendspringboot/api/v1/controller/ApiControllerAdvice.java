package cc.grum.base.backendspringboot.api.v1.controller;

import cc.grum.base.backendspringboot.api.v1.base.BaseApiException;
import cc.grum.base.backendspringboot.api.v1.base.BaseApiResponse;
import cc.grum.base.backendspringboot.api.v1.base.exception.ForbiddenException;
import cc.grum.base.backendspringboot.api.v1.base.exception.UnAuthException;
import cc.grum.base.backendspringboot.api.v1.model.ResponseConstants;
import cc.grum.base.backendspringboot.api.v1.model.response.ResApiStatus;
import cc.grum.base.backendspringboot.core.utils.LogUtils;
import cc.grum.base.backendspringboot.core.utils.MessageUtils;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.Locale;

@RestControllerAdvice
public class ApiControllerAdvice {

    private final MessageSourceAccessor messageSource;
    public ApiControllerAdvice(@Qualifier("validatorMessageSource") MessageSourceAccessor messageSource) {
        this.messageSource = messageSource;
    }

    // 400 필수 파라메터 오류 발생 처리
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseApiResponse handleParamMissError(MissingServletRequestParameterException e) throws Exception {
        LogUtils.i( "MissingServletRequestParameterException = {}", e.getMessage() );
        return BaseApiResponse.error(
                new BaseApiException(ResponseConstants.API_HTTP_BAD_REQUEST));
    }

    /*
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseApiResponse constraintViolationException(ConstraintViolationException e) throws Exception {
        LogUtils.i( "ConstraintViolationException = {}", e.getMessage() );
        return BaseApiResponse.error(
                new BaseApiException(ResponseConstants.API_HTTP_BAD_REQUEST));
    }
     */

    // 400 파라메터 Type 오류 발생 처리
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public BaseApiResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String message = MessageUtils.getMessage(
                ResponseConstants.API_RESULT_MSG_PREFIX + ResponseConstants.API_HTTP_BAD_REQUEST
                , new String[] {e.getName()});
        return BaseApiResponse.error(
                new BaseApiException(ResponseConstants.API_HTTP_BAD_REQUEST, message));
    }


    // 401 권한 오류 발생 (권한이 없는 페이지 접근한 경우)
    @ExceptionHandler(UnAuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseApiResponse handleUnAuthorized(UnAuthException e) throws Exception {
        LogUtils.e( "UnAuthException: {}", e.getMessage() );
        return BaseApiResponse.error(e);
    }

    // 403 권한 오류 발생 (권한이 없는 페이지 접근한 경우)
    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public BaseApiResponse handleForbiddenError(ForbiddenException e) throws Exception {
        LogUtils.e( "ForbiddenException: {}", e.getMessage() );
        return BaseApiResponse.error(e);
    }

    // 404 페이지를 찾을수 없는 경우(?)
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseApiResponse noHandlerFoundException(NoHandlerFoundException e) {
        LogUtils.e( "NoHandlerFoundException: {}", e.getMessage() );
        return BaseApiResponse.error(
                new BaseApiException(ResponseConstants.API_HTTP_NOT_FOUND));
    }

    // request body 요청 missing
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.LENGTH_REQUIRED)
    public BaseApiResponse httpMessageNotReadableException(HttpMessageNotReadableException e) throws Exception {
        LogUtils.e( "HttpMessageNotReadableException: {}", e.getMessage() );
        return BaseApiResponse.error(
                new BaseApiException(ResponseConstants.API_HTTP_REQUEST_BODY_MISS));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public BaseApiResponse httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) throws Exception {
        LogUtils.e( "HttpRequestMethodNotSupportedException: {}", e.getMessage() );
        return BaseApiResponse.error(
                new BaseApiException(ResponseConstants.API_HTTP_METHOD_NOT_ALLOWED));

    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public BaseApiResponse httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) throws Exception {
        LogUtils.e( "HttpMediaTypeNotSupportedException: {}", e.getMessage() );
        return BaseApiResponse.error(
                new BaseApiException(ResponseConstants.API_HTTP_REQUEST_MEDIA_TYPE_MISS));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseApiResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {
        // LogUtils.e( "MethodArgumentNotValidException: {}", e.getMessage() );
        StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Locale locale = LocaleContextHolder.getLocale();
        for (FieldError fieldError : fieldErrors) {
            message.append(messageSource.getMessage(fieldError.getDefaultMessage(), locale));
        }
        return BaseApiResponse.error(ResponseConstants.API_HTTP_BAD_REQUEST, message.toString());
    }

    // 예상된 예외 발생 처리 (BaseException 확장 Exception)
    @ExceptionHandler(BaseApiException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public BaseApiResponse handlePlatformException(BaseApiException e) {
        LogUtils.e( "handlePlatformException: {}", e.getMessage() );
        // e.printStackTrace();
        return BaseApiResponse.error(e);
    }

    // 500 오류 발생 처리
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseApiResponse handleServerError(Exception e) {
        LogUtils.e( "internalServerError: {}", e.getMessage() );
        e.printStackTrace();
        return BaseApiResponse.error(
                new BaseApiException(ResponseConstants.API_HTTP_SERVER_ERROR));
    }

}
