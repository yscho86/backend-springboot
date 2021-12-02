package cc.grum.base.backendspringboot.api.v1.model.request;

import cc.grum.base.backendspringboot.api.v1.model.RequestConstants;
import cc.grum.base.backendspringboot.core.db.entity.MemberMaster;
import lombok.*;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class ReqSignUp {

    @NotBlank(message = "api.request.reqSignup.valid.loginId.blank")
    @Size(min = 1, max = 100, message = "api.request.reqSignup.valid.loginId.size")
    private String loginId;

    @NotBlank(message = "api.request.reqSignup.valid.nickname.blank")
    private String nickname;

    @NotBlank(message = "api.request.reqSignup.valid.password.blank")
    private String password;

    @NotBlank(message = "api.request.reqSignup.valid.mobileNumber.blank")
    @Size(min = 1, max = 20, message = "api.request.reqSignup.valid.mobileNumber.size")
    @Pattern(regexp = RequestConstants.PATTERN_MOBILE)
    private String mobileNumber;

    public MemberMaster toEntity() {
        return MemberMaster.builder()
                .loginId(loginId)
                .nickname(nickname)
                .password(password)
                .build();
    }

    public interface Signup {}
}
