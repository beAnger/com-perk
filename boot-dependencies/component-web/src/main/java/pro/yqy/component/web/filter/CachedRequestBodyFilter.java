package pro.yqy.component.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import pro.yqy.component.web.common.HttpRequestWrapper;

import java.io.IOException;

/**
 * @Description:
 * @Author: YQY
 * @Date: 2022/07/25/17:36
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "cachedRequestBodyFilter")
public class CachedRequestBodyFilter implements Filter, Ordered {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(httpServletRequest);
        filterChain.doFilter(requestWrapper, servletResponse);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
