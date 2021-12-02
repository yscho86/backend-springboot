package cc.grum.base.backendspringboot.api.v1.model.request;

import cc.grum.base.backendspringboot.api.v1.model.RequestConstants;
import cc.grum.base.backendspringboot.core.db.entity.MemberMaster;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class ReqSignIn {

    @NotBlank(message = "api.request.reqSignup.valid.loginId.blank")
    @Size(min = 1, max = 100, message = "api.request.reqSignup.valid.loginId.size")
    private String loginId;

    @NotBlank(message = "api.request.reqSignup.valid.password.blank")
    private String password;

}
