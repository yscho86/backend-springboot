package cc.grum.base.backendspringboot.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController extends BaseController {

    @GetMapping(value = "/error")
    @ResponseBody
    public String error(HttpServletRequest request,
                        HttpServletResponse response,
                        HttpSession session,
                        Model model) throws Exception {
        return "error";
    }
}
