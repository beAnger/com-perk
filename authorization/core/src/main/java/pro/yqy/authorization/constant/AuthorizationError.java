package pro.yqy.authorization.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pro.yqy.component.web.error.IRestStatus;

@Getter
@AllArgsConstructor
public enum AuthorizationError implements IRestStatus {
    account_format_incorrect(100001, "账号信息格式错误")

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
