package cc.grum.base.backendspringboot.api.v1.exception;

import cc.grum.base.backendspringboot.api.v1.base.BaseApiException;
import cc.grum.base.backendspringboot.api.v1.model.ResponseConstants;

public class MemberNotFoundException extends BaseApiException {

	public MemberNotFoundException() {
		super(ResponseConstants.API_MEMBER_NOT_FOUND);
	}
}
