package cc.grum.base.backendspringboot.api.v1.base.exception;

import cc.grum.base.backendspringboot.api.v1.base.BaseApiException;
import cc.grum.base.backendspringboot.api.v1.model.ResponseConstants;

public class UnAuthException extends BaseApiException {

	public UnAuthException() {
		super(ResponseConstants.API_HTTP_UNAUTHORIZED);
	}


}
