package pro.yqy.component.web.error.enumeration;

import pro.yqy.component.web.error.IRestStatus;

public enum RestStatus implements IRestStatus {
    SUCCESS(200, "success"),
    ERROR(-1, "未知错误"),
    FEIGN_HYSTRIX(-3, "服务繁忙，请稍后重试"),
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
