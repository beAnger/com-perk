package pro.yqy.authorization.model.bean.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pro.yqy.authorization.model.enumeration.account.AccountActionType;
import pro.yqy.authorization.model.enumeration.account.ChannelType;

@Getter
@Setter
@NotNull
@Schema(name = "发送验证码请求对象")
public class SendVerificationCodeRequestBean {
    @NotNull(message = "渠道类型不能为空或不支持的渠道类型")
    @Schema(description = "渠道类型")
    private ChannelType channelType;

    @NotBlank(message = "渠道账号不能为空")
    @Schema(description = "渠道账号")
    private String identity;

    @NotNull(message = "作用类型不能为空")
    @Schema(description = "作用类型")
    private AccountActionType actionType;
}
