package cc.grum.base.backendspringboot.core.db.mapper;

import cc.grum.base.backendspringboot.api.v1.model.request.ReqSignIn;
import cc.grum.base.backendspringboot.core.db.entity.MemberMaster;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberMapper extends BaseMapper<MemberMaster> {
    Optional<MemberMaster> findByLoginId(String loginId);
    Optional<MemberMaster> findByLoginIdNPassword(ReqSignIn loginId, String password);
    Boolean existByLoginId(String loginId);
    void insertMember(MemberMaster memberMaster);

}

