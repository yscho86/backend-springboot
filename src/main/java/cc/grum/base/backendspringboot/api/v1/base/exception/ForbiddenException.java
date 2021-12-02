package cc.grum.base.backendspringboot.api.v1.base.exception;

import cc.grum.base.backendspringboot.api.v1.base.BaseApiException;
import cc.grum.base.backendspringboot.api.v1.model.ResponseConstants;

public class ForbiddenException extends BaseApiException {
	
	public ForbiddenException() {
		super(ResponseConstants.API_HTTP_FORBIDDEN);
	}
}
