package uk.co.cnoversea.web.interceptor;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import uk.co.cnoversea.common.string.Simple;
import uk.co.cnoversea.web.controller.AbstractController;
import uk.co.cnoversea.web.util.HttpServletLogger;
import uk.co.cnoversea.web.vo.AuthVO;
import uk.co.cnoversea.web.vo.RequestVO;
import uk.co.cnoversea.web.vo.ResponseVO;
import uk.co.cnoversea.web.vo.WebVO;
import uk.co.cnoversea.web.wapper.BodyContentHttpServletRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationInterceptor extends AbstractController implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    private Map<String, String> userAuthData;
    private Map<String, String> whiteURI;


    public AuthenticationInterceptor(){
        userAuthData = new HashMap<>();
        userAuthData.put("benson", "8b318d4320db1");
        userAuthData.put("major","68e9fa5615b86");

        whiteURI = new HashMap<>();
        whiteURI.put("/api/user/regist", "");
        whiteURI.put("/api/user/login", "");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("preHandle");
        HttpServletLogger.logRequest(request);
        try {

            String contentType = request.getContentType();
            contentType = contentType.toLowerCase();
            if(contentType.indexOf("application/json") == -1){
                sendResponse(ResponseVO.CODE_ERR, "request.contendType must by application/json", response);
                return false;
            }

            BodyContentHttpServletRequestWrapper wrapper = new BodyContentHttpServletRequestWrapper(request);
            String body = wrapper.getBodyString();
            logger.info("request body : {}", body);

            String uri = request.getRequestURI();
            if(whiteURI.get(uri) != null){
                logger.info("white request uri : {}", uri);
                return true;
            }

            if(Simple.StringNotNull(body)){
                RequestVO<WebVO> req = JSON.parseObject(body, new RequestVO<WebVO>().getClass());
                AuthVO auth = req.getAuth();
                String userPass = userAuthData.get(auth.getUserKey());
                boolean authResult = auth.authenticate(userPass);
                if(!authResult){
                    sendResponse(ResponseVO.CODE_ERR, "authentication fail", response);
                }
                return authResult;
            }
        }catch(Exception e){
            logger.error("authenticate fail", e);
            sendResponse(ResponseVO.CODE_ERR, e.getMessage(), response);
        }
        return false;
    }

    private void sendResponse(int code, String msg, HttpServletResponse response) throws IOException {
        ResponseVO<Map<String, Object>> rspObj = genResponse(code, msg);
        response.getWriter().println(JSON.toJSONString(rspObj));
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        logger.info("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        logger.info("afterCompletion");
    }
}
