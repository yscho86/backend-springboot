package cc.grum.base.backendspringboot.api.controller;

import cc.grum.base.backendspringboot.api.v1.IndexApiController;
import cc.grum.base.backendspringboot.api.v1.service.IndexApiService;
import cc.grum.base.backendspringboot.core.utils.LogUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
@DisplayName("@IndexApiController Test")
class IndexApiControllerTest extends BaseApiControllerTest {

    @Autowired
    IndexApiController indexApiController;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void contextLoads() {
        Assertions.assertThat(indexApiController).isNot(null);
    }

    @Test
    @DisplayName("@GET /")
    public void index() throws Exception {
        URI uri = new URI("/");

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        LogUtils.d("response: {}", result);
        //Verify request succeed
    }

    @Test
    @DisplayName("@POST /")
    void postIndex() throws Exception {
        URI uri = new URI("/");
        Map<String, Object> request = new HashMap<>();

        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        LogUtils.d("response: {}", result);
    }

    @Test
    void error() {
    }
}