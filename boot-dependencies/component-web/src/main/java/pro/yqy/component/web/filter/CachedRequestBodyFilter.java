//package pro.yqy.component.web.filter;
//
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.stereotype.Component;
//import pro.yqy.component.web.common.HttpRequestWrapper;
//import pro.yqy.component.web.common.RestLog;
//import pro.yqy.component.web.conf.RestLogConfig;
//
//import java.io.IOException;
//
///**
// * @Description:
// * @Author: YQY
// * @Date: 2022/07/25/17:36
// */
//@Component
//@WebFilter(urlPatterns = "/*", filterName = "cachedRequestBodyFilter")
//public class CachedRequestBodyFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(httpServletRequest);
//        if (RestLogConfig.isEnable()) {
//            RestLog.logRequest(requestWrapper);
//        }
//
//        filterChain.doFilter(requestWrapper, servletResponse);
//    }
//}
