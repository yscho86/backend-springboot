package cc.grum.base.backendspringboot.api.v1.model;

public class ResponseConstants {

    public static final Integer API_HTTP_OK = 200;
    public static final Integer API_HTTP_BAD_REQUEST = 400;
    public static final Integer API_HTTP_UNAUTHORIZED = 401;
    public static final Integer API_HTTP_FORBIDDEN = 403;
    public static final Integer API_HTTP_NOT_FOUND = 404;
    public static final Integer API_HTTP_METHOD_NOT_ALLOWED = 405;
    public static final Integer API_HTTP_REQUEST_BODY_MISS = 411;
    public static final Integer API_HTTP_REQUEST_MEDIA_TYPE_MISS = 415;
    public static final Integer API_HTTP_UNPROCESSABLE_ENTITY = 422;
    public static final Integer API_HTTP_SERVER_ERROR = 500;
    public static final Integer API_HTTP_SERVICE_UNAVAILABLE = 503;


    public static final Integer API_HTTP_LOGIN_FAILED = 1001;
    public static final Integer API_HTTP_EXISTED_MEMBER = 1002;

    public static final Integer API_MEMBER_NOT_FOUND = 2001;
    public static final Integer API_HTTP_S3_UPLOAD_FAILED = 3001;

    public static final String API_RESULT_MSG_PREFIX = "api.response.msg.";

}
