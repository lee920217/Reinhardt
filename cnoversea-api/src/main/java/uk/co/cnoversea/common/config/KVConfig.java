package uk.co.cnoversea.common.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KVConfig {

    private static final Logger logger = LoggerFactory.getLogger(KVConfig.class);

    private Properties prop = new Properties();
    private Map<String, String> cfgMap = new HashMap<String, String>();

    private KVConfig() {
    }

    //优先加载当前classpath目录下的
    //这样可以支持当一个module引用另一个module时
    //可以在当前module的classpath下增加自己需要的配置文件
    //而不影响其他module
    public static KVConfig loadConfig(String path) throws Exception {
        //中间加载过程出错用info输出不用error
        KVConfig cfg = new KVConfig();
        if (cfg.loadConfigInClasspath(path)) {
            return cfg;
        }
        if (cfg.loadConfigInPath(path)) {
            return cfg;
        }
        if (cfg.loadConfigInJar(path)) {
            return cfg;
        }
        logger.error("Load config from {} fail", path);
        throw new Exception("Load config from " + path + " fail");
    }

    //读取指定位置的配置文件
    private boolean loadConfigInPath(String path) {
        logger.info("try loading config file path : {}", path);
        InputStream fis = null;
        try {
            File file = new File(path);
            fis = new FileInputStream(file);
            prop.load(fis);
            load(prop);
        } catch (Exception e) {
            //e.printStackTrace();
            logger.info("Load config {} from path fail", path, e);
            return false;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
            }
        }
        return true;
    }

    //读取jar包中的配置文件
    private boolean loadConfigInJar(String path) {
        logger.info("try loading config file in jar : {}", path);
        InputStream is = null;
        try {
            //FileInputStream inputStream1 = new FileInputStream("out.properties");
            //InputStream inputStream2 = a.class.getResourceAsStream("/in.properties");
            //is = new Object().getClass().getResourceAsStream(path);
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
            prop.load(is);
            load(prop);
        } catch (Exception e) {
            //e.printStackTrace();
            logger.info("Load config {} in jar fail", path, e);
            return false;
        } finally {
            try {
                if (is != null) is.close();
            } catch (Exception e) {
            }
        }
        return true;
    }

    //读取classpath下的配置文件
    private boolean loadConfigInClasspath(String path) {
        String root = "";
        InputStream fis = null;
        try {
            root = Thread.currentThread().getContextClassLoader().getResource("//").getPath();
            File file = new File(root + path);
            logger.info("try loading config file in classpath : {}", file.getAbsolutePath());
            fis = new FileInputStream(file);
            prop.load(fis);
            load(prop);
            logger.info("load success");
        } catch (Exception e) {
            //e.printStackTrace();
            logger.info("Load config {}{} from classpath fail", root, path, e);
            return false;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
            }
        }
        return true;
    }

    private void load(Properties prop) {
        Enumeration<?> e = prop.propertyNames();
        while (e.hasMoreElements()) {
            String key = e.nextElement().toString();
            cfgMap.put(key, prop.getProperty(key));
        }
    }

    public String getConfig(String key) {
        return cfgMap.get(key);
    }

    public String getConfig(String key, String defaultVal) {
        String val = cfgMap.get(key);
        return val == null ? defaultVal : val;
    }

    public Map<String, String> getConfig() {
        return this.cfgMap;
    }

    public Properties getProperties() {
        return this.prop;
    }

    public void displayConfig() {
        for (String key : cfgMap.keySet()) {
            //System.out.println(key + " = " + cfgMap.get(key));
            logger.info(key + "=" + cfgMap.get(key));
        }
    }

    public static void main(String[] args) {
        logger.trace("==============trace");
        logger.debug("==============debug");
        logger.info("==============info");
        logger.warn("==============warn");
        logger.error("==============error");
        try {
            KVConfig config = KVConfig.loadConfig("common.properties");
            config.displayConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
