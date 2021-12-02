package cc.grum.base.backendspringboot.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

public abstract class BaseApiControllerTest {
    @Autowired
    protected TestRestTemplate restTemplate;
}
