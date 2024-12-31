package pro.yqy.component.web.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import pro.yqy.component.web.property.RestLogProperties;
import org.apache.commons.lang.ArrayUtils;

@Configuration
public class RestLogConfig {

    private static RestLogProperties restLogProperties;

    /**
     * 最大打印的日志
     */
    public static final Long MAX_LOG_LENGTH = 10000L;

    @Autowired
    public void setRestLogProperties(RestLogProperties restLogProperties) {
        RestLogConfig.restLogProperties = restLogProperties;
    }

    public static boolean isEnable() {
        return restLogProperties.getEnable() != null && !restLogProperties.getEnable();
    }

    /**
     * 忽略打印日志的接口
     */
    private static final String[] IGNORE_PATHS = { "/swagger", "/swagger-ui",
            "/swagger-ui/*", "/webjars", "/druid", "/info", "/health", "/v2/api-docs",
            "/v3/api-docs", "/csrf", "/favicon", "/favicon.ico","/metrics", "/hystrix.stream", "/env",
            "/configprops", "/actuator", "/swagger-resources", "doc.html" };

    public static String[] getIgnorePaths() {
        return (restLogProperties.getExcludeUrls() == null || "null".equals(restLogProperties.getExcludeUrls()))
                ? IGNORE_PATHS : (String[]) ArrayUtils.addAll(IGNORE_PATHS, restLogProperties.getExcludeUrls().split(","));
    }
}
