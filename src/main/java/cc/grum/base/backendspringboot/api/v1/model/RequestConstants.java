package cc.grum.base.backendspringboot.api.v1.model;

public class RequestConstants {
    public static final String PATTERN_AT  = "@";
    public static final String PATTERN_YN = "^[YN]$";
    public static final String PATTERN_NUM = "^[0-9]*$";
    public static final String PATTERN_MOBILE = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
    public static final String PATTERN_KOREAN = "[ㄱ-ㅎ가-힣]";

    public static final String PATTERN_EMAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

}
