package cc.grum.base.backendspringboot.api.service;

import cc.grum.base.backendspringboot.api.v1.service.IndexApiService;
import cc.grum.base.backendspringboot.core.db.mapper.MemberMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class IndexApiServiceTest {
    private MemberMapper memberMapper = Mockito.mock(MemberMapper.class);
    private IndexApiService indexApiService;

    @BeforeEach
    public void setUp(){
    }

    @Test
    void totalMember() {
    }
}
