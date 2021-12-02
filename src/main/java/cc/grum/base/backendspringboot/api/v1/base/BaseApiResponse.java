package cc.grum.base.backendspringboot.api.v1.base;

import cc.grum.base.backendspringboot.api.v1.model.response.ResApiStatus;
import cc.grum.base.backendspringboot.core.utils.DateTimeUtils;
import cc.grum.base.backendspringboot.core.utils.LogUtils;
import cc.grum.base.backendspringboot.core.utils.RequestUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class BaseApiResponse<T> {

	private Integer code;
	private String message;
	private String now;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;

	public static <T> BaseApiResponse success() {
		return success(null);
	}

	public static <T> BaseApiResponse success(T result) {
		return response(ResApiStatus.OK, result);
	}

	public static <T> BaseApiResponse error(BaseApiException e) {
		return response(ResApiStatus.fromValue(e.getCode()), null);
	}

	public static <T> BaseApiResponse error(ResApiStatus responseStatus) {
		return response(responseStatus, null);
	}

	public static <T> BaseApiResponse error(int code, String message) {

		return response(code, message, null);
	}


	private static <T> BaseApiResponse response(ResApiStatus responseStatus, T result) {
		return response(responseStatus.getStatus(), responseStatus.getMessage(), result);
	}

	private static <T> BaseApiResponse response(int code, String message, T result) {
		BaseApiResponse response = BaseApiResponse.builder()
				.code(code)
				.message(message)
				.now(DateTimeUtils.now())
				.data(result)
				.build();

		LogUtils.d("[{}] http-response: {}", RequestUtils.getRequestId(), new Gson().toJson(response));

		return response;
	}


}