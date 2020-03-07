package uk.co.cnoversea.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.time.ZoneId;

public class Util {

    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    public static final String ENCODING = "UTF-8";
    public static final ZoneId TIMEZONE_E8 = ZoneId.of("Asia/Shanghai");

    public static final String HOSTNAME = getHostName();
    public static final String IP = getIP();
    public static final String PROCESS_ID = getProcessId();

    private static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        }catch(Exception e) {
            logger.error("常量初始化失败", e);
            return "localhost";
        }
    }

    //不准，先凑合用吧
    private static String getIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        }catch(Exception e) {
            logger.error("常量初始化失败", e);
            return "127.0.0.1";
        }
    }

    private static String getProcessId(){
        String pidAndHost = ManagementFactory.getRuntimeMXBean().getName();
        logger.info("pidAndHost : {}", pidAndHost);
        if(pidAndHost != null){
            String[] idArr = pidAndHost.split("@");
            if(idArr != null && idArr.length >= 2){
                return idArr[0];
            }
        }
        return "";
    }

    public static void main(String[] args) {
        logger.info("HOSTNAME : {}", HOSTNAME);
        logger.info("IP : {}", IP);
        logger.info("PROCESS_ID : {}", PROCESS_ID);
    }
}
