package pro.yqy.component.web.error.enumeration;

import pro.yqy.component.web.error.IRestStatus;

public enum RestStatus implements IRestStatus {
    SUCCESS(200, "success"),
    ERROR(-1, "未知错误"),
    UNKNOWN_ERROR(-1, "未知异常"),
    UN_AUTHORIZATION(401, "当前用户权限不足"),
    TOKEN_INVALID(4001, "Token无效"),
    INVALID_MODEL_FIELDS(4002, "字段校验非法"),
    INVALID_PARAMS_CONVERSION(4003, "参数类型非法"),
    INVALID_JSON_PARSE(4004, "JSON解释出错"),
    ;

    private final int code;
    private final String msg;

    RestStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return msg;
    }
}
