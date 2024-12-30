package pro.yqy.component.web.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import pro.yqy.component.web.conf.RestLogConfig;
import pro.yqy.component.web.singleton.SingletonItem;
import pro.yqy.component.web.util.HttpUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RestLog {
    private static final ThreadLocal<Long> startTime = new ThreadLocal<>();

    public static void logRequest(HttpRequestWrapper request) throws JsonProcessingException {
        if (!RestLogConfig.isEnable()) {
            startTime.set(-1L);
            return;
        }
        startTime.set(System.currentTimeMillis());
        // 忽略打印请求地址
        String requestPath = request.getServletPath();
        for (String pathStart : RestLogConfig.getIgnorePaths()) {
            if (requestPath.startsWith(pathStart)) {
                startTime.set(-1L);
                return;
            }
        }
        // 打印请求信息
        Map<String, Object> logMap = new HashMap<>(8);
        String params = request.getContentLength() > RestLogConfig.MAX_LOG_LENGTH
                ? ("(Large Content [" + request.getContentLength() + "])") : request.toString();
        if (StringUtils.isEmpty(params)) {
            params = "( Empty Content )";
        }
        logMap.put("Method", request.getMethod());
        logMap.put("Path", request.getServletPath());
        logMap.put("Host", HttpUtils.getRemoteAddr(request));
        logMap.put("Content-Length", request.getContentLength());
        logMap.put("Cookies", request.getCookies());
        logMap.put("Referer", request.getHeader("Referer"));
        logMap.put("Content-Type", request.getContentType());
        logMap.put("User-Agent", request.getHeader("User-Agent"));
        log.info("接收请求:\n{}\n参数内容:{}", SingletonItem.OBJECT_MAPPER.writeValueAsString(logMap), params);
    }

    public static void logResponse(Object result) {
        if (!RestLogConfig.isEnable() || !log.isDebugEnabled()) {
            return;
        }
        try {
            if (result != null) {
                if (result instanceof byte[]) {
                    result = "文件流";
                }
                else {
                    result = SingletonItem.OBJECT_MAPPER.writeValueAsString(result);
                }
            }
            Long requestTime = startTime.get();
            if (requestTime != null && requestTime > 0) {
                log.info("执行{}毫秒，返回结果:{}", System.currentTimeMillis() - requestTime, result);
                startTime.remove();
            } else if (requestTime == null) {
                log.info("执行结束，返回结果:{}", result);
            }
        } catch (Exception e) {
            log.error("LOG_RESPONSE_ERROR", e);
        }
    }
}
