package cc.grum.base.backendspringboot.api.v1.controller;

import cc.grum.base.backendspringboot.api.v1.base.BaseApiController;
import cc.grum.base.backendspringboot.api.v1.base.BaseApiResponse;
import cc.grum.base.backendspringboot.api.v1.model.request.ReqSignIn;
import cc.grum.base.backendspringboot.api.v1.model.request.ReqSignUp;
import cc.grum.base.backendspringboot.api.v1.model.response.ResSignIn;
import cc.grum.base.backendspringboot.api.v1.service.MemberSignApiService;
import cc.grum.base.backendspringboot.core.db.entity.MemberToken;
import cc.grum.base.backendspringboot.core.utils.LogUtils;
import cc.grum.base.backendspringboot.core.utils.RequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RequestMapping(value="/api/v1")
@RestController
public class MemberSignApiController extends BaseApiController {

    private final MemberSignApiService memberSignApiService;

    //DB에서 사용자 요청
    @GetMapping(value = {"/member/{pid}"})
    public BaseApiResponse member(@PathVariable Long pid) throws Exception {
        return BaseApiResponse.success(memberSignApiService.getById(pid));
    }

    @PostMapping(value = {"/member"})
    public BaseApiResponse signup(@RequestBody @Validated ReqSignUp reqSignUp) throws Exception {
        LogUtils.d("params: {}", reqSignUp);

        RequestUtils.getToken();

        return BaseApiResponse.success();
    }

    //비회원이 token 요청
    @PostMapping(path = "/access/key")
    public BaseApiResponse accessKey(HttpServletRequest request) throws Exception {

        ResSignIn resSignIn = memberSignApiService.accessKey(request);

        return BaseApiResponse.success(resSignIn);
    }

    //비회원이 refresh token으로 token 재발급 요청
    @PutMapping(path = "/sign/non-member/{pid}")
    public BaseApiResponse refreshNoneMember(@RequestBody @Validated MemberToken memberToken, @PathVariable Long pid) throws Exception {
        LogUtils.d("params: {}", memberToken);

        memberToken.setLoginId(memberSignApiService.getById(pid).getUsername());

        ResSignIn resSignIn = memberSignApiService.refresh(memberToken);

        return BaseApiResponse.success(resSignIn);
    }

    //로그인
    @PostMapping(path = "/sign/signin")
    public BaseApiResponse signIn(@RequestBody @Validated ReqSignIn reqSignIn) throws Exception {
        LogUtils.d("params: {}", reqSignIn);
        ResSignIn resSignIn = memberSignApiService.signIn(reqSignIn);

        return BaseApiResponse.success(resSignIn);
    }

    //로그아웃
    @PostMapping(path = "/sign/signOut")
    public BaseApiResponse signOut(@RequestBody @Validated ReqSignIn reqSignIn) throws Exception {
        LogUtils.d("params: {}", reqSignIn);
        ResSignIn resSignIn = memberSignApiService.signOut(reqSignIn);

        return BaseApiResponse.success(resSignIn);
    }

    //회원이 refresh token으로 token 재발급 요청
    @PutMapping(path = "/sign/refresh")
    public BaseApiResponse refresh(@RequestBody @Validated MemberToken memberToken) throws Exception {
        LogUtils.d("params: {}", memberToken);
        ResSignIn resSignIn = memberSignApiService.refresh(memberToken);

        return BaseApiResponse.success(resSignIn);
    }



}
