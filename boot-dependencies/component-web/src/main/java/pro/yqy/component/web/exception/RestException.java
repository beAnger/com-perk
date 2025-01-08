package pro.yqy.component.web.exception;


import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.yqy.component.web.error.IRestStatus;
import pro.yqy.component.web.error.enumeration.RestStatus;
import pro.yqy.component.web.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class RestException extends RuntimeException {
    protected Logger logger;

    private Integer code;
    private String msg;
    private String error;

    public void setError(Exception e, boolean isShowErrorDetail) {
        logger.error("系统异常", e);
        if (isShowErrorDetail) {
            this.code = RestStatus.ERROR.code();
            if (Objects.isNull(this.msg)) {
                this.msg = RestStatus.ERROR.message();
            }
            this.error = StringUtils.toString(e);
        }
    }

    public RestException(String msg, Exception e) {
        this(msg, e, true);
    }

    public RestException(String msg, Exception e, Boolean isShowErrorDetail) {
        super(msg, null, false, isShowErrorDetail);
        this.logger = LoggerFactory.getLogger(RestException.class);
        this.code = RestStatus.ERROR.code();
        this.msg = msg;
        this.setError(e, isShowErrorDetail);
    }

    public RestException(Exception e) {
        this(e, true);
    }

    public RestException(Exception e, boolean isShowErrorDetail) {
        super(e.getMessage(), null, false, isShowErrorDetail);
        this.code = RestStatus.ERROR.code();
        this.setError(e, isShowErrorDetail);
        this.logger = LoggerFactory.getLogger(RestException.class);
    }

    public RestException(IRestStatus restStatus) {
        this.code = restStatus.code();
        this.msg = restStatus.message();
        this.logger = LoggerFactory.getLogger(RestException.class);
    }

    public RestException(RestStatus restStatus, String error) {
        this.code = restStatus.code();
        this.msg = restStatus.message();
        this.error = error;
        this.logger = LoggerFactory.getLogger(RestException.class);
    }

    public RestException(int code, String msg) {
        this(code, msg, null);
    }

    public RestException(int code, String msg, String error) {
        this.code = code;
        this.msg = msg;
        this.error = error;
        this.logger = LoggerFactory.getLogger(RestException.class);
    }

    public RestException showError(boolean isShowErrorDetail) {
        if (isShowErrorDetail) {
            this.error = null;
        }

        return this;
    }

    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("code", code);
        map.put("msg", msg);
        if (Objects.nonNull(error)) {
            map.put("error", error);
        }
        return map;
    }
}
