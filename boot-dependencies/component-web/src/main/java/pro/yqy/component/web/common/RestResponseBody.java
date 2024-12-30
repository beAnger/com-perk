package pro.yqy.component.web.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import pro.yqy.component.web.adapter.RestExceptionAdapter;
import pro.yqy.component.web.adapter.RestResponseBodyAdapter;
import pro.yqy.component.web.error.IRestStatus;
import pro.yqy.component.web.error.enumeration.RestStatus;
import pro.yqy.component.web.exception.RestException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Configuration
@ControllerAdvice(assignableTypes = {BaseRestController.class}, annotations = {RestControllerAdvice.class})
public class RestResponseBody implements ResponseBodyAdvice<Object> {
    @Value("${boot.exception.showErrorDetail:true}")
    private boolean outputError;

    private RestExceptionAdapter restExceptionAdapter;

    private RestResponseBodyAdapter restResponseBodyAdapter;

    @Autowired(required = false)
    public void setRestExceptionAdapter(RestExceptionAdapter restExceptionAdapter) {
        this.restExceptionAdapter = restExceptionAdapter;
    }

    @Autowired(required = false)
    public void setRestResponseBodyAdapter(RestResponseBodyAdapter restResponseBodyAdapter) {
        this.restResponseBodyAdapter = restResponseBodyAdapter;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if (restResponseBodyAdapter != null) {
            return restResponseBodyAdapter.handle(body, returnType, selectedContentType,
                    selectedConverterType, request, response);
        }
        // 如果返回的MediaType不是application/json，就不进行封装处理
        if (!selectedContentType.includes(MediaType.APPLICATION_JSON)) {
            return body;
        }
        Map<String, Object> result;
        // 若返回内容为空的时候，将空Map作为返回对象
        if (Objects.isNull(body)) {
            body = new HashMap<>(0);
        }
        switch (body) {
            case RestException restException -> {
                if (restExceptionAdapter != null) {
                    restException = restExceptionAdapter.handle(restException);
                }
                if (log.isWarnEnabled()) {
                    log.warn(restException.getMsg());
                }
                result = restException.asMap();
            }
            case IRestStatus restStatus -> {
                result = new HashMap<>(2);
                result.put("code", restStatus.getCode());
                result.put("msg", restStatus.getMessage());
            }
            case ResultMessage<?> resultMessage -> {
                logResponse(resultMessage);

                return body;
            }
            case byte[] ignored -> {
                return body;
            }
            default -> {
                result = RestStatus.SUCCESS.toMap();
                // 将返回的数据,封装进data中
                result.put("data", body);
            }
        }
        logResponse(result);
        if (!outputError) {
            result.remove("error");
        }
        return result;
    }

    private void logResponse(Object response) {
        if (!log.isDebugEnabled()) {
            return;
        }
        try {
            RestLog.logResponse(response);
        } catch (Exception e) {
            log.error("LOG_RESPONSE_ERROR", e);
        }
    }
}
