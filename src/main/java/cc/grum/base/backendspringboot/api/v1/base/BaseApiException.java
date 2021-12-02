package cc.grum.base.backendspringboot.api.v1.base;

import cc.grum.base.backendspringboot.api.v1.model.ResponseConstants;
import cc.grum.base.backendspringboot.core.utils.DateTimeUtils;
import cc.grum.base.backendspringboot.core.utils.MessageUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseApiException extends RuntimeException {

	private Integer code;
	private String message;
	private String now;

	public BaseApiException(Integer code) {
		this.code = code;
		this.message = MessageUtils.getMessage(ResponseConstants.API_RESULT_MSG_PREFIX + code);
		this.now = DateTimeUtils.now();
	}

	public BaseApiException(Integer code, String message) {
		this.code = code;
		this.message = message;
		this.now = DateTimeUtils.now();
	}

}
