package com.api.automation.config;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigManager {

    private static Logger logger = Logger.getLogger(ConfigManager.class);
    private static Properties properties;

    static {
        loadProperties();
    }

    private static void loadProperties() {
        properties = new Properties();
        try {
            String configPath = System.getProperty("config.path", "src/test/resources/config.properties");
            properties.load(new FileInputStream(configPath));
            logger.info("Configuration loaded from: " + configPath);
        } catch (Exception e) {
            logger.error("Error loading config: " + e.getMessage(), e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static String getBaseURI() {
        return getProperty("base.uri");
    }

    public static int getRequestTimeout() {
        return Integer.parseInt(getProperty("request.timeout", "5000"));
    }

    public static int getRetryCount() {
        return Integer.parseInt(getProperty("retry.count", "3"));
    }

    public static long getRetryDelay() {
        return Long.parseLong(getProperty("retry.delay", "1000"));
    }
}
