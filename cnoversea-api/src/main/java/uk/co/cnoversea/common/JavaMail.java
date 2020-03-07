package uk.co.cnoversea.common;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.cnoversea.common.config.KVConfig;

import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaMail {

    static {
        //https://blog.52itstyle.com/archives/1779/?utm_source=tuicool&utm_medium=referral
        //不设置这个值，附件名超长了就会乱码
        System.getProperties().setProperty("mail.mime.splitlongparameters", "false");

    }

    private static final Logger logger = LoggerFactory.getLogger(JavaMail.class);
    public static final long ATTACHMENT_SIZE_LIMIT = 1024 * 1024 * 29;//29MB 附件大小上限 服务器端限制是30MB

    public static final String DEFAULT_CONFIG_FILE = "mail.properties";
    public static final String MAIL_SERVER_HOSTNAME_CK = "mail.server.hostname";
    public static final String MAIL_SERVER_PORT_CK = "mail.server.port";
    public static final String MAIL_SERVER_ACCOUNT_CK = "mail.server.account";
    public static final String MAIL_SERVER_PASS_CK = "mail.server.pass";
    public static final String MAIL_SERVER_FROM_MAIL_CK= "mail.server.from.mail";
    public static final String MAIL_SERVER_FROM_NICK_CK = "mail.server.from.nick";

    public static final String MAIL_SERVER_TO_TITLE_CK = "mail.server.to.title";
    public static final String MAIL_SERVER_TO_CONTENT_CK = "mail.server.to.content";

    private boolean debug = false;
    private boolean startTLSEnabled = true;
    private String charset = "UTF-8";

    private KVConfig config;

    private String hostName;
    private int port;
    private String senderName;
    private String senderPassword;
    private String fromMail;
    private String fromNick;

    private String title;
    private String content;
    private List<String> sendToList;// 多个之间用分号;隔开
    private List<String> attachmentList;

    public JavaMail() throws Exception {
        this(DEFAULT_CONFIG_FILE);
    }

    public JavaMail(String configFile) throws Exception {
        config = KVConfig.loadConfig(configFile);
        hostName = config.getConfig(MAIL_SERVER_HOSTNAME_CK);
        port = Integer.valueOf(config.getConfig(MAIL_SERVER_PORT_CK));
        senderName = config.getConfig(MAIL_SERVER_ACCOUNT_CK);
        senderPassword = config.getConfig(MAIL_SERVER_PASS_CK);
        fromMail = config.getConfig(MAIL_SERVER_FROM_MAIL_CK);
        fromNick = config.getConfig(MAIL_SERVER_FROM_NICK_CK);
        title = config.getConfig(MAIL_SERVER_TO_TITLE_CK);
        content = config.getConfig(MAIL_SERVER_TO_CONTENT_CK);
    }

    public void send(String charset, boolean debug, String hostName, boolean startTLSEnabled, int port,
                     String senderName, String senderPassword, String fromMail, String fromNick, List<String> sendToList, String title,
                     String content, List<String> attachmentList) throws EmailException {

        HtmlEmail email = new HtmlEmail();

        email.setDebug(debug);
        email.setStartTLSEnabled(startTLSEnabled);
        email.setCharset(charset);

        email.setHostName(hostName);
        email.setSmtpPort(port);
        email.setAuthentication(senderName, senderPassword);
        email.setFrom(fromMail, fromNick + "@" + Util.HOSTNAME);

        for(String sendTo : sendToList) {
            if (sendTo != null) {
                String[] sendToArr = sendTo.split(";");
                for(String oneSendTo : sendToArr) {
                    if(oneSendTo.contains("@")) {
                        email.addTo(oneSendTo);
                    }
                }
            }
        }

        email.setSubject(title);
        email.setHtmlMsg(content);

        long totalSize = 0l;
        if(attachmentList !=null) {
            for (String attachmentName : attachmentList) {
                File file = new File(attachmentName);
                if (file != null && file.exists()) {
                    if(totalSize + file.length() >= ATTACHMENT_SIZE_LIMIT) {
                        logger.error("因总附件总大小超长被忽略的附件 : " + attachmentName);
                        continue;
                    }else {
                        totalSize += file.length();
                    }

                    //email.attach(file);
                    //if(true)continue;

                    EmailAttachment attachment = new EmailAttachment();
                    attachment.setPath(attachmentName);
                    //attachment.setName(file.getName());
                    logger.info("附件名称 : " + file.getName());
                    logger.info("附件路径 : " + attachmentName);
                    try {
                        attachment.setName(MimeUtility.encodeText(file.getName()));
                    } catch (UnsupportedEncodingException e) {
                        logger.error("附件名称编码失败", e);
                    }
                    //attachment.setDisposition(EmailAttachment.ATTACHMENT);
                    //attachment.setDescription("attachment");
                    email.attach(attachment);
                }
            }
        }

        email.send();
    }

    public void send(String hostName, String port, String senderName, String senderPassword, String fromMail,
                     String fromNick, String title, String content, List<String> sendTo, List<String> attachmentList) throws Exception {
        this.send(this.charset, this.debug, hostName, this.startTLSEnabled, Integer.valueOf(port), senderName,
                senderPassword, fromMail, fromNick, sendTo, title, content, attachmentList);
    }

    public void send(String title, String content, List<String> sendTo, List<String> attachmentList)
            throws Exception {
        this.send(this.charset, this.debug, this.hostName, this.startTLSEnabled, this.port, this.senderName,
                this.senderPassword, this.fromMail, this.fromNick, sendTo, title, content, attachmentList);
    }

    public void send() throws EmailException {
        this.send(this.charset, this.debug, this.hostName, this.startTLSEnabled, this.port, this.senderName,
                this.senderPassword, this.fromMail, this.fromNick, this.sendToList, this.title, this.content,
                this.attachmentList);
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public boolean isStartTLSEnabled() {
        return startTLSEnabled;
    }

    public void setStartTLSEnabled(boolean startTLSEnabled) {
        this.startTLSEnabled = startTLSEnabled;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPassword() {
        return senderPassword;
    }

    public void setSenderPassword(String senderPassword) {
        this.senderPassword = senderPassword;
    }

    public String getFromMail() {
        return fromMail;
    }

    public void setFromMail(String fromMail) {
        this.fromMail = fromMail;
    }

    public String getFromNick() {
        return fromNick;
    }

    public void setFromNick(String fromNick) {
        this.fromNick = fromNick;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getSendToList() {
        return sendToList;
    }

    public void setSendToList(List<String> sendToList) {
        this.sendToList = sendToList;
    }

    public List<String> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<String> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public static void main(String[] args) throws IOException {
        // 需要配置vmargs
        // -Djavax.net.ssl.trustStore=/Users/sefarious/workspace/spanner/spanner-tools/src/build/ca/jssecacerts

        String title = "tilte";
        String content = "content";
        List<String> sendTo = Arrays.asList("zhangxiaopeng@panda.tv;wushunxin@panda.tv");
        List<String> attachmentList = null;
        args = new String[]{title, content, "zhangxiaopeng@panda.tv;wushunxin@panda.tv"};

        try {
            if(args != null && args.length >=3) {
                title = args[0];
                content = args[1];
                sendTo = Arrays.asList(args[2]);
            }else {
                //不传必要的参数就报错结束
                throw new Exception("参数不足");
            }
            if(args != null && args.length >=4) {
                attachmentList = new ArrayList<String>();
                for(int index = 3; index < args.length; index++) {
                    attachmentList.add(args[index]);
                }
            }
            new JavaMail().send(title, content, sendTo, attachmentList);
        } catch (Exception e) {
            logger.error("邮件发送失败", e);
            System.exit(1);
        }
    }

}
