package pers.hui.common.spark.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <code>ConfigUtils</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/13 0:22.
 *
 * @author Gary.Hu
 */
public enum ConfigUtils {
    /**
     * INIT SINGLE
     */
    INSTANCE;
    private Map<String, Properties> propertiesMap = new HashMap<>();

    private static final String DEFAULT_CONFIG = "common.cfg";

    ConfigUtils() {
        propertiesMap.put(DEFAULT_CONFIG, new Properties());
        loadConfig(DEFAULT_CONFIG);
    }

    public String get(String key, String defaultVal) {
        String val = get(key);
        return null == val ? defaultVal : val;
    }

    public String get(String key) {
        return propertiesMap.get(DEFAULT_CONFIG).getProperty(key);
    }

    public String get(String configName, String key, String defaultVal) {
        if (null == propertiesMap.get(configName)) {
            propertiesMap.put(configName, new Properties());
            loadConfig(configName);
        }
        Properties properties = propertiesMap.get(configName);
        return null == properties.get(key) ? defaultVal : properties.getProperty(key);
    }

    public void loadConfig(String configName) {
        try (InputStream in = ConfigUtils.class.getResourceAsStream("/".concat(configName))) {
            if (null != in) {
                propertiesMap.get(configName).load(in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
