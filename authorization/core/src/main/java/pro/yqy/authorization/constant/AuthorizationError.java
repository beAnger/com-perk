package pro.yqy.authorization.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pro.yqy.component.web.error.IRestStatus;

@Getter
@AllArgsConstructor
public enum AuthorizationError implements IRestStatus {
    illegal_identity_format(100001, "账号信息格式错误"),
    request_too_frequently(100002, "请求过于频繁，请稍候再重试"),
    no_such_message_send_channel(100003, "系统发送消息时发生了错误"),
    verify_code_send_failed(100004, "发送验证码失败"),
    verify_code_error(10005, "验证码错误"),

    ;

    private final int code;
    private final String msg;

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return msg;
    }
}
