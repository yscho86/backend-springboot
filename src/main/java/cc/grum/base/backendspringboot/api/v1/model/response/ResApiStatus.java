package cc.grum.base.backendspringboot.api.v1.model.response;

import cc.grum.base.backendspringboot.api.v1.model.ResponseConstants;
import cc.grum.base.backendspringboot.core.utils.MessageUtils;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ResApiStatus {

    OK(ResponseConstants.API_HTTP_OK),
    BAD_REQUEST(ResponseConstants.API_HTTP_BAD_REQUEST),  // 파라메터 오류
    UNAUTHORIZED(ResponseConstants.API_HTTP_UNAUTHORIZED), // 인증(권한) 오류
    FORBIDDEN(ResponseConstants.API_HTTP_FORBIDDEN),  // 권한 오류
    NOT_FOUND(ResponseConstants.API_HTTP_NOT_FOUND),  // 페이지 Not Found
    METHOD_NOT_ALLOWED(ResponseConstants.API_HTTP_METHOD_NOT_ALLOWED),
    REQUEST_BODY_MISS(ResponseConstants.API_HTTP_REQUEST_BODY_MISS),
    REQUEST_MEDIA_TYPE_MISS(ResponseConstants.API_HTTP_REQUEST_MEDIA_TYPE_MISS),
    UNPROCESSABLE_ENTITY(ResponseConstants.API_HTTP_UNPROCESSABLE_ENTITY),

    SERVER_ERROR(ResponseConstants.API_HTTP_SERVER_ERROR),   // 서버에러
    SERVICE_UNAVAILABLE(ResponseConstants.API_HTTP_SERVICE_UNAVAILABLE),

    MEMBER_NOT_FOUND(ResponseConstants.API_MEMBER_NOT_FOUND),
    LOGIN_FAILED(ResponseConstants.API_HTTP_LOGIN_FAILED)   // 로그인 실패
    ;

    private int status;
    private String message;

    ResApiStatus(int status) {
        this.status = status;
        this.message = MessageUtils.getMessage(ResponseConstants.API_RESULT_MSG_PREFIX + status);
    }

    ResApiStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ResApiStatus fromValue(int value) throws IllegalArgumentException {
        return Arrays.stream(ResApiStatus.values())
                .filter(e -> e.status == value)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Unsupported result status: %s.", value)));
    }
}
