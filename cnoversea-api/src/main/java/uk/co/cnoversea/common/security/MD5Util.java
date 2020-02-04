package uk.co.cnoversea.common.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    private static final Logger log = LoggerFactory.getLogger(MD5Util.class);

    private static final String algorithm = "MD5";

    public static final ThreadLocal<MessageDigest> messageDigest = new ThreadLocal<MessageDigest>() {
        protected MessageDigest initialValue() {
            try {
                return MessageDigest.getInstance(algorithm);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        }
    };

    public static String queryMD5(String data) {
        try {
            MessageDigest md = messageDigest.get();
            md.update(data.getBytes(StandardCharsets.UTF_8));
            byte[] b = md.digest();
            int i;
            StringBuilder buf = new StringBuilder();

            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }

            return buf.toString();
        } catch (Exception e) {
            log.error(e.toString());
        }
        return "null";
    }

}
