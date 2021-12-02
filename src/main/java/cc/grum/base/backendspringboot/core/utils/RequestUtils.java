package cc.grum.base.backendspringboot.core.utils;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class RequestUtils {
	private static ServletRequestAttributes requestAttributes() throws Exception {
		return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	}

	public static HttpServletRequest request() {
		try {
			HttpServletRequest httpServletRequest = requestAttributes().getRequest();
			return httpServletRequest;
		} catch (Exception e) {
		}

		return null;
	}

	public static Map<String, Object> initParamMap() {
		HttpServletRequest request = request();
		if (request==null) return new HashMap<>();

		Map<String, Object> requestMap = new HashMap<String, Object>(request.getParameterMap());

		try {
			Set<String> keys = requestMap.keySet();
			for (String key : keys) {
				String[] values = (String[]) requestMap.get(key);
				if (values.length == 1) {
					requestMap.put(key, cleanXSS(values[0]));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return requestMap;
	}

	private static JSONObject getParams(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		Enumeration<String> params = request.getParameterNames();
		try {
			while (params.hasMoreElements()) {
				String param = params.nextElement();
				String replaceParam = param.replaceAll("\\.", "-");
				jsonObject.put(replaceParam, request.getParameter(param));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	// 크로스사이트 스크립팅 취약점 방지
	public static String cleanXSS(String value) {
		value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
		value = value.replaceAll("'", "&#39;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replaceAll("script", "");
		return value;
	}

	// Request Header에서 Authorization Bearer 토큰
	public static String getToken() {
		HttpServletRequest request = request();
		if (request==null) return "UnKnow";

		String authHeader = request.getHeader("Authorization");
		LogUtils.d( "Auth Token Header = " + authHeader );

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return null;
		}

		String token = null;
		try {
			token = authHeader.substring(7);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}

	public static String getRemoteIp() {
		return getRemoteIp(request());
	}

	public static String getRemoteIp(HttpServletRequest request) {
		if (request==null) return "UnKnow";
		String ip = request.getHeader("X-Forwarded-For");
		// LogUtils.d(">>>> X-FORWARDED-FOR : " + ip);

		if (ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
			// LogUtils.d(">>>> Proxy-Client-IP : " + ip);
		}
		if (ip == null) {
			ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직
			// LogUtils.d(">>>> WL-Proxy-Client-IP : " + ip);
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			// LogUtils.d(">>>> HTTP_CLIENT_IP : " + ip);
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			// LogUtils.d(">>>> HTTP_X_FORWARDED_FOR : " + ip);
		}
		if (ip == null) {
			ip = request.getRemoteAddr();
		}

		// LogUtils.d(">>>> Result : IP Address : "+ip);
		return ip;
	}

	public static String getUserAgent() {
		HttpServletRequest request = request();
		if (request==null) return "UnKnow";
		return request.getHeader("User-Agent");
	}

	public static String getRequestId() {
		HttpServletRequest request = request();
		if (request==null) return "UnKnow";
		return (String)request.getAttribute("request-id");
	}

	/*public static Map<String, Object> getHeaderParams(HttpServletRequest request) {

		if (request==null) return new HashMap<>();

		Map<String, Object> headerMap = new HashMap<String, Object>();


		Enumeration headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()) {
			String name = (String)headerNames.nextElement();
			String value = request.getHeader(name);

			headerMap.put(name, value);

		}

		return headerMap;
	}*/
}
