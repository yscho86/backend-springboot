package cc.grum.base.backendspringboot.api.v1;

import cc.grum.base.backendspringboot.api.v1.base.BaseApiException;
import cc.grum.base.backendspringboot.api.v1.model.response.ResMember;
import cc.grum.base.backendspringboot.api.v1.service.IndexApiService;
import cc.grum.base.backendspringboot.api.v1.base.BaseApiController;
import cc.grum.base.backendspringboot.api.v1.base.BaseApiResponse;
import cc.grum.base.backendspringboot.api.v1.base.exception.ForbiddenException;
import cc.grum.base.backendspringboot.api.v1.base.exception.UnAuthException;
import cc.grum.base.backendspringboot.core.utils.LogUtils;
import cc.grum.base.backendspringboot.core.utils.RequestUtils;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class IndexApiController extends BaseApiController {

    private final IndexApiService indexApiService;

    @GetMapping(value = {"", "/_health"})
    public BaseApiResponse index(HttpServletRequest request) {
        return BaseApiResponse.success();
    }

    @GetMapping(value = {"/member/{id}"})
    public BaseApiResponse<ResMember> member(HttpServletRequest request) {
        return BaseApiResponse.success();
    }

    @PostMapping(value = "")
    public BaseApiResponse postIndex(@RequestBody Map<String, Object> paramMap) {
        LogUtils.d("[{}] http-request: {}", RequestUtils.getRequestId(), paramMap);
        Long total = indexApiService.total();
        return BaseApiResponse.success();
    }

    @GetMapping(value = "/exception/{code}")
    public BaseApiResponse error(@PathVariable Integer code) {
        if (code == 403) throw new ForbiddenException();
        if (code == 401) throw new UnAuthException();

        return BaseApiResponse.error(new BaseApiException(code));
    }

}
