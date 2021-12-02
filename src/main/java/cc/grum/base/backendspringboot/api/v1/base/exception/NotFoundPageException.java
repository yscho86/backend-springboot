package cc.grum.base.backendspringboot.api.v1.base.exception;

import cc.grum.base.backendspringboot.api.v1.base.BaseApiException;
import cc.grum.base.backendspringboot.api.v1.model.ResponseConstants;

public class NotFoundPageException extends BaseApiException {

    public NotFoundPageException() {
        super(ResponseConstants.API_HTTP_NOT_FOUND);
    }
}
