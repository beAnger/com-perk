package pro.yqy.authorization.model.bean.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pro.yqy.authorization.model.enumeration.account.RegisterType;

import java.io.Serializable;

@Getter
@Setter
@NotNull
public class RegisterRequestBean implements Serializable {
    @NotNull(message = "注册方式不能为空或不支持的注册类型")
    @Schema(description = "注册方式")
    private RegisterType registerType;

    @NotBlank(message = "id不能为空")
    @Schema(description = "对应注册方式的id")
    private String identity;

    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码")
    private String password;

    @NotBlank(message = "验证码不能为空")
    @Schema(description = "验证码")
    private String verificationCode;
}
