package uk.co.cnoversea.common.string;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Simple {

    public static void main(String[] args) {

    }

    private static final Logger logger = LoggerFactory.getLogger(Simple.class);

    public static final String dateFormatExp = "yyyy-MM-dd HH:mm:ss";
    public static final String dateFormatExp1 = "yyyyMMddHHmmss";
    public static final DateFormat standDateFormator = new SimpleDateFormat(dateFormatExp);
    public static final DateFormat standDateFormator1 = new SimpleDateFormat(dateFormatExp1);

    private static AtomicLong al = new AtomicLong();

    public static boolean StringNull(String s){
        if(s == null){
            return true;
        }
        s = s.trim();
        if("".equals(s)){
            return true;
        }
        if("null".equals(s)){
            return true;
        }
        if("undefined".equals(s)){
            return true;
        }
        return false;
    }

    public static boolean StringNotNull(String s){
        if(s == null){
            return false;
        }
        s = s.trim();
        if("".equals(s)){
            return false;
        }
        if("null".equals(s)){
            return false;
        }
        if("undefined".equals(s)){
            return false;
        }
        return true;
    }

    public static String strArr2Str(String[] strArr) {
        if(strArr == null || strArr.length < 1) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for(String str : strArr) {
            sb.append(",");
            sb.append(str);
        }
        String sbStr = sb.toString();
        return sbStr.length() > 0 ? sbStr.substring(1) : sbStr;
    }

    public static String generateSerialNumber(){
        long serial = al.getAndIncrement();
        String dateStr = standDateFormator1.format(new Date());
        return dateStr + "_" + serial;
    }

    public static void logArgs(String[] args){
        if(args == null){
            logger.debug("args = null");
            return;
        }
        for(String arg : args){
            logger.debug("arg=" + arg);
        }
    }

    public static String getHostNameForLiunx() {
        try {
            return (InetAddress.getLocalHost()).getHostName();
        } catch (UnknownHostException uhe) {
            String host = uhe.getMessage(); // host = "hostname: hostname"
            if (host != null) {
                int colon = host.indexOf(':');
                if (colon > 0) {
                    return host.substring(0, colon);
                }
            }
            return "UnknownHost";
        }
    }

    public static String getHostName() {
        String hostName = null;
        if (System.getenv("COMPUTERNAME") != null) {
            hostName = System.getenv("COMPUTERNAME");
        } else {
            hostName = getHostNameForLiunx();
        }
        logger.info("HOST_NAME=" + hostName);
        return hostName;
    }

    public static String getUserName() {
        String userName = System.getenv("USERNAME");
        if(StringNull(userName)){
            userName = System.getProperty("user.name");
        }
        logger.info("USER_NAME=" + userName);
        return userName;
    }

    public static boolean numberCheck(String numStr, int length) {
        if (StringNull(numStr)) {
            return false;
        }
        return numStr.matches("[0-9]{" + length + "}");
    }

    public static boolean simpleDateCheck(String dateStr) {
        if (StringNull(dateStr)) {
            return false;
        }
        return dateStr.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}");
    }

    public static String reverse(String str) {
        return new StringBuilder(str).reverse().toString();
    }

    public static String genUUID32(){
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "");
        return uuid;
    }
}
