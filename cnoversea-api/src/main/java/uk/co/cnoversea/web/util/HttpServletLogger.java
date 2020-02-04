package uk.co.cnoversea.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/*
 * 此工具类仅用于开发阶段分析提交内容
 * 不建议用于开发环境
 */
public class HttpServletLogger {
    private static final Logger logger = LoggerFactory.getLogger(HttpServletLogger.class);

    public static void logRequest(HttpServletRequest request){
        logger.info("authType = {}", request.getAuthType());
        logger.info("characterEncoding = {}", request.getCharacterEncoding());
        logger.info("contextPath = {}", request.getContextPath());
        logger.info("contentType = {}", request.getContentType());
        logger.info("contentLength = {}", request.getContentLength());
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length > 0){
            for(Cookie cookie : cookies){
                logger.info("cookie.comment = {}", cookie.getComment());
                logger.info("cookie.domain = {}", cookie.getDomain());
                logger.info("cookie.maxAge = {}", cookie.getMaxAge());
                logger.info("cookie.name = {}", cookie.getName());
                logger.info("cookie.path = {}", cookie.getPath());
                logger.info("cookie.cecure = {}", cookie.getSecure());
                logger.info("cookie.value = {}", cookie.getValue());
                logger.info("cookie.version = {}", cookie.getVersion());
            }
        }
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            logger.info("header.{} = {}", headerName, request.getHeader(headerName));
        }
        logger.info("method = {}", request.getMethod());
        Enumeration<String> parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String parameterName = parameterNames.nextElement();
            logger.info("parameter.{} = {}", parameterName, request.getParameter(parameterName));
        }
        logger.info("pathInfo = {}", request.getPathInfo());
        logger.info("queryString = {}", request.getQueryString());
        logger.info("remoteUser = {}", request.getRemoteUser());
        logger.info("requestedSessionId = {}", request.getRequestedSessionId());
        logger.info("requestURI = {}", request.getRequestURI());
        logger.info("requestURL = {}", request.getRequestURL());
        logger.info("servletPath = {}", request.getServletPath());
        //logger.info(" = {}", );

        request.getAttributeNames();
    }
}
