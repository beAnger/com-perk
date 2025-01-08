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
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import pro.yqy.component.web.adapter.RestResponseBodyAdapter;
import pro.yqy.component.web.error.IRestStatus;
import pro.yqy.component.web.error.enumeration.RestStatus;
import pro.yqy.component.web.exception.RestException;

@Slf4j
@Configuration
@RestControllerAdvice(assignableTypes = {BaseRestController.class})
public class RestResponseBody implements ResponseBodyAdvice<Object> {
    @Value("${boot.exception.showErrorDetail:true}")
    private boolean outputError;

    private RestResponseBodyAdapter restResponseBodyAdapter;

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
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        if (restResponseBodyAdapter != null) {
            return restResponseBodyAdapter.handle(body, returnType, selectedContentType,
                    selectedConverterType, request, response);
        }
        Object result = switch (body) {
            case RestException restException -> {
                if (!outputError) {
                    yield new ResultMessage<>(restException.getCode(), restException.getMsg(), null);
                }
                yield new ResultMessage<>(restException.getCode(), restException.getMsg(), restException.getError());
            }
            case IRestStatus restStatus -> new ResultMessage<Void>(restStatus.code(), restStatus.message(), null);
            case ResultMessage<?> ignored -> body;
            case byte[] ignored -> body;
            case String str -> new ResultMessage<>(RestStatus.SUCCESS, str).toString();
            default -> new ResultMessage<>(RestStatus.SUCCESS, body);
        };

        RestLog.logResponse(result);
        return result;
    }


}
