package pro.yqy.component.web.common;

import lombok.Getter;
import pro.yqy.component.web.error.IRestStatus;
import pro.yqy.component.web.error.enumeration.RestStatus;

import java.io.Serializable;

@Getter
public class ResultMessage<T> implements Serializable {
    private final int code;
    private final String msg;
    private final T data;

    public ResultMessage(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultMessage(IRestStatus restStatus, T data) {
        this.code = restStatus.code();
        this.msg = restStatus.message();
        this.data = data;
    }

    public static <T> ResultMessage<T> ok(T data) {
        return new ResultMessage<>(RestStatus.SUCCESS, data);
    }
}
