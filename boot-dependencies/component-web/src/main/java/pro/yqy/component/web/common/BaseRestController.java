package pro.yqy.component.web.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pro.yqy.component.web.adapter.RestExceptionAdapter;
import pro.yqy.component.web.error.enumeration.RestStatus;
import pro.yqy.component.web.exception.RestException;
import pro.yqy.component.web.util.StringUtils;

import javax.security.sasl.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public abstract class BaseRestController {
    @Value("${boot.exception.showErrorDetail:true}")
    private boolean showErrorDetail;

    private RestExceptionAdapter restExceptionAdapter;

    @Autowired(required = false)
    public void setRestExceptionAdapter(RestExceptionAdapter restExceptionAdapter) {
        this.restExceptionAdapter = restExceptionAdapter;
    }

    @ExceptionHandler({RestException.class})
    public Object processRestException(RestException e) {
        return this.handleExceptionAdapter(e);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Object processApiException(MethodArgumentNotValidException e) {

        List<String> msgList = new ArrayList<>();
        for (ObjectError objectError : e.getBindingResult().getAllErrors()) {
            msgList.add(objectError.getDefaultMessage());
        }

        log.error("字段校验异常-MethodArgumentNotValidException: {}, detail: {}", StringUtils.toString(msgList), e.getMessage());
        return this.handleExceptionAdapter(
                new RestException(RestStatus.INVALID_MODEL_FIELDS, StringUtils.toString(msgList))
        );
    }

    @ExceptionHandler({BindException.class})
    public Object doBindException(BindException e) {
        List<String> msgList = new ArrayList<>();

        for (ObjectError objectError : e.getBindingResult().getAllErrors()) {
            msgList.add(objectError.getDefaultMessage());
        }

        return this.handleExceptionAdapter(
                new RestException(RestStatus.INVALID_PARAMS_CONVERSION, StringUtils.toString(msgList))
        );
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public Object doHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String msg = e.getMessage();
        if (msg.startsWith("JSON parse error")) {
            String start = "field : ";
            String end = ";";
            msg = "字段[" + msg.substring(msg.indexOf(start) + start.length(), msg.indexOf(end)) + "]解析出错";
            return this.handleExceptionAdapter(new RestException(RestStatus.INVALID_JSON_PARSE, msg));
        } else {
            log.error("HttpMessageNotReadableException: {}, detail: {}", msg, e.getMessage());
            return this.handleExceptionAdapter(new RestException(msg, e));
        }
    }

    @ExceptionHandler({AccessDeniedException.class})
    public Object handleAccessDeniedException(Exception e) {
        return this.handleExceptionAdapter(new RestException(RestStatus.UN_AUTHORIZATION));
    }

    @ExceptionHandler({AuthenticationException.class})
    public Object handleAuthenticationException(Exception e) {
        return this.handleExceptionAdapter(new RestException(RestStatus.TOKEN_INVALID));
    }

    @ExceptionHandler({Exception.class})
    public Object handleException(Exception e) {
        RestException restException = new RestException(RestStatus.UNKNOWN_ERROR.getMessage(), e);
        return this.handleExceptionAdapter(restException);
    }

    public Object handleExceptionAdapter(RestException restException) {
        return this.restExceptionAdapter != null ?
                this.restExceptionAdapter.handle(restException.showError(this.showErrorDetail))
                : restException.showError(this.showErrorDetail);
    }

}
