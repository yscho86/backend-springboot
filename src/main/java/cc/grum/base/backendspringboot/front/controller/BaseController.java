package cc.grum.base.backendspringboot.front.controller;

import org.springframework.stereotype.Controller;

@Controller
public abstract class BaseController {

    protected final String FORM_DATE_FORMAT = "yyyy/MM/dd";

    protected final Integer DEFAULT_PAGE_NO = 1;
    protected final Integer DEFAULT_PAGE_SIZE = 20;

}
