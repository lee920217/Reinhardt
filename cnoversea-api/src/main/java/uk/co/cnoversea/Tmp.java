package uk.co.cnoversea;

import uk.co.cnoversea.api.dao.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Tmp {
    public static void main(String[] args) {
        System.out.println(queryMd5("95ca98bf7da130f131d3ba8b78f93711", "2020-01-31"));
        User user = new User();
        user.setUid("uid");
        System.out.println("unexists user : uid = " + user.getUid() + ", email = " + user.getEmail());

    }

    public static String queryMd5(String key, String day) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((key + day).getBytes(StandardCharsets.UTF_8));
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
            e.printStackTrace();
        }
        return "null";
    }

}
