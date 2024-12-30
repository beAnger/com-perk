package pro.yqy.component.web.adapter;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

public interface RestResponseBodyAdapter {
    Object handle(Object data,
                  MethodParameter returnType,
                  MediaType selectedContentType,
                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                  ServerHttpRequest request, ServerHttpResponse response);
}
