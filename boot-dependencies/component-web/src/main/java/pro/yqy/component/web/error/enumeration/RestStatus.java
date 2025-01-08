package pro.yqy.component.web.error.enumeration;

import com.fasterxml.jackson.core.JsonProcessingException;
import pro.yqy.component.web.error.IRestStatus;
import pro.yqy.component.web.singleton.SingletonItem;

import java.util.HashMap;
import java.util.Map;

public enum RestStatus implements IRestStatus {
    SUCCESS(0, "success"),
    ERROR(-1, "未知错误"),
    FEIGN_HYSTRIX(-3, "服务繁忙，请稍后重试"),
    UN_AUTHORIZATION(401, "当前用户权限不足"),
    NO_AUTHORIZATION(401, "没有权限，请联系管理员授权"),
    UNKNOWN_ERROR(-1, "未知异常"),
    TOKEN_INVALID(40001, "Token无效"),
    INVALID_MODEL_FIELDS(40002, "字段校验非法"),
    INVALID_PARAMS_CONVERSION(40003, "参数类型非法"),
    INVALID_JSON_PARSE(40004, "JSON解释出错"),
    HTTP_MESSAGE_NOT_READABLE(41001, "HTTP消息不可读"),
    REQUEST_METHOD_NOT_SUPPORTED(41002, "不支持的HTTP请求方法"),
    BAD_SQL_GRAMMAR(42001, "SQL语句执行出错"),
    DUPLICATE_KEY(42002, "数据库中已存在该记录"),
    UNAUTHORIZED(401, "未授权访问"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "内容不存在"),
    SERVER_ERROR(500, "服务器异常");
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

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>(4);
        map.put("code", code);
        map.put("msg", msg);
        return map;
    }

    public String toJson() throws JsonProcessingException {
        return SingletonItem.OBJECT_MAPPER.writeValueAsString(this.toMap());
    }

}
