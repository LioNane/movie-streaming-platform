package org.project.streamingapi.patterns.singleton;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class AppConfigManager {

    private final Properties props = new Properties();

    private AppConfigManager() {
        try (InputStream in = AppConfigManager.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException ignored) {
        }
    }

    private static class Holder {
        private static final AppConfigManager INSTANCE = new AppConfigManager();
    }

    public static AppConfigManager getInstance() {
        return Holder.INSTANCE;
    }

    public String get(String key) {
        return props.getProperty(key);
    }

    public String getOrDefault(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
}
