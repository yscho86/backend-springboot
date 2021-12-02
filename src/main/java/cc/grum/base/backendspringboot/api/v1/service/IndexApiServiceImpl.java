package cc.grum.base.backendspringboot.api.v1.service;

import cc.grum.base.backendspringboot.core.db.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class IndexApiServiceImpl implements IndexApiService {

    private final MemberMapper memberMapper;

    @Override
    public Long total() {

        Long total = Long.valueOf(memberMapper.total());
        return total;
    }
}
