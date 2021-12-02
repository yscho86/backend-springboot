package cc.grum.base.backendspringboot.api.v1.service;

import cc.grum.base.backendspringboot.api.v1.model.request.ReqSignIn;
import cc.grum.base.backendspringboot.api.v1.model.response.ResMember;
import cc.grum.base.backendspringboot.api.v1.model.response.ResSignIn;
import cc.grum.base.backendspringboot.core.db.entity.MemberToken;

import javax.servlet.http.HttpServletRequest;

public interface MemberSignApiService {
    ResMember getById(Long pid) throws Exception;
    ResSignIn signIn(ReqSignIn reqSignIn) throws Exception;
    ResSignIn signOut(ReqSignIn reqSignIn) throws Exception;
    ResSignIn refresh(MemberToken memerToken) throws Exception;
    void autoLogin(String username, String password);
    ResSignIn accessKey(HttpServletRequest request) throws Exception;


}
