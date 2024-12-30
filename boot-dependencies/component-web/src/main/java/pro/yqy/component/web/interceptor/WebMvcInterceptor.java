package pro.yqy.component.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;
import pro.yqy.component.web.common.BaseRestController;
import pro.yqy.component.web.common.RestLog;
import pro.yqy.component.web.error.enumeration.RestStatus;
import pro.yqy.component.web.singleton.SingletonItem;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class WebMvcInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        if ((!response.isCommitted()
                || modelAndView != null
                || DefaultServletHttpRequestHandler.class == handler.getClass())) {

            response.setContentType("application/json;charset=utf-8");
            Map<String, Object> result = RestStatus.SUCCESS.toMap();
            if (modelAndView != null && "error".equals(modelAndView.getViewName())) {
                result = RestStatus.ERROR.toMap();
                result.put("data", modelAndView.getModel());
                return;
            }
            if (handler instanceof HandlerMethod
                    && ((HandlerMethod) handler).getBean() instanceof BaseRestController
                    && ((HandlerMethod) handler).isVoid()) {
                result.put("data", new HashMap<>(0));
            }
            try (final Writer writer = response.getWriter()) {
                String responseStr = SingletonItem.OBJECT_MAPPER.writeValueAsString(result);
                writer.write(responseStr);
                if (log.isDebugEnabled()) {
                    RestLog.logResponse(result);
                }
            }
            response.flushBuffer();
        }
    }
}
