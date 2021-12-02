package cc.grum.base.backendspringboot.config.security.token;

public interface AuthToken<T> {
    boolean validate();
    T getData();
}
