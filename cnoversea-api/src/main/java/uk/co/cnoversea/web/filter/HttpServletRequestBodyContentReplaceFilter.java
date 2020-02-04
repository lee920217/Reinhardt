package uk.co.cnoversea.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.cnoversea.web.wapper.BodyContentHttpServletRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//@WebFilter(filterName = "httpServletRequestReplacedFilter", urlPatterns = {"/*"})// TODO 多个用逗号分隔
public class HttpServletRequestBodyContentReplaceFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(HttpServletRequestBodyContentReplaceFilter.class);

    @Override
    public void destroy() {
        logger.info("--------------过滤器销毁------------");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        logger.info("--------------过滤器 doFilter------------");
        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest) {
            requestWrapper = new BodyContentHttpServletRequestWrapper((HttpServletRequest) request);
        }
        if (requestWrapper == null) {
            logger.info("--------------过滤器 doFilter request------------");
            chain.doFilter(request, response);
        } else {
            logger.info("--------------过滤器 doFilter requestWrapper------------");
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        logger.info("--------------过滤器初始化------------");
    }

}