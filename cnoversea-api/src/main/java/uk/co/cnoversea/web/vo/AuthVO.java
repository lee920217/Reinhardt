package uk.co.cnoversea.web.vo;

import uk.co.cnoversea.common.date.DateConvertor;
import uk.co.cnoversea.common.security.MD5Util;
import uk.co.cnoversea.common.string.Simple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class AuthVO extends WebVO {

    private static final Logger logger = LoggerFactory.getLogger(AuthVO.class);

    public static final long timeout = 1000 * 60 * 60 * 24;//60秒超时
    public static final String SPLIT = "|";
    public static final long disMillSecond = 1000;

    private String userKey;
    private long timestamp;
    private String verifyKey;

    public AuthVO(String userKey, long timestamp, String verifyKey) {
        this.userKey = userKey;
        this.timestamp = timestamp;
        this.verifyKey = verifyKey;
    }

    public AuthVO(HttpServletRequest request) throws Exception {
        userKey = request.getParameter("auth.userKey");
        timestamp = Long.parseLong(request.getParameter("auth.timestamp"));
        verifyKey = request.getParameter("auth.verifyKey");
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getVerifyKey() {
        return verifyKey;
    }

    public void setVerifyKey(String verifyKey) {
        this.verifyKey = verifyKey;
    }

    public boolean authenticate(String userPass) {

        userPass = MD5Util.queryMD5(userPass);
        if (Simple.StringNull(userKey)) {
            logger.error("userKey is null");
            return false;
        }
        if (Simple.StringNull(verifyKey)) {
            logger.error("verifyKey is null");
            return false;
        }
        if (Simple.StringNull(userPass)) {
            logger.error("userPass is null");
            return false;
        }
        long now = DateConvertor.currentMillis();
        if (now - timestamp >= timeout) {
            logger.error("now - timestamp >= timeout, now = {}, timestamp = {}, timeout = {}", now, timestamp, timeout);
            return false;
        }
        if (timestamp >= now + disMillSecond) {
            logger.error("timestamp >= now + disMillSecond, timestamp = {}, now = {}, disMillSecond = {}", timestamp, now, disMillSecond);
            return false;
        }
        String calVerifyKey = MD5Util.queryMD5(userKey + SPLIT + userPass + SPLIT + timestamp);
        logger.info("calVerifyKey = {}, verifyKey = {}", calVerifyKey, verifyKey);

        return calVerifyKey.equals(verifyKey);
    }

}
