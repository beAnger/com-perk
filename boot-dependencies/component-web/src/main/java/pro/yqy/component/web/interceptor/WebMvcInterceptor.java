package pro.yqy.component.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;
import pro.yqy.component.web.common.BaseRestController;
import pro.yqy.component.web.common.RestLog;
import pro.yqy.component.web.conf.RestLogConfig;
import pro.yqy.component.web.error.enumeration.RestStatus;
import pro.yqy.component.web.singleton.SingletonItem;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class WebMvcInterceptor implements HandlerInterceptor, Ordered {

    @Override
    public boolean preHandle(HttpServletRequest servletRequest,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if (RestLogConfig.isEnable()) {
            RestLog.logRequest(servletRequest);
        }
        return true;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
