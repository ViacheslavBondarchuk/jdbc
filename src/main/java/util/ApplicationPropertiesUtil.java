package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public final class ApplicationPropertiesUtil {
    private final static String PROPERTY_PATH = "application.properties";
    private final static Properties properties = new Properties();

    static {
        try (InputStream inputStream = ApplicationPropertiesUtil.class.getClassLoader().getResourceAsStream(PROPERTY_PATH)) {
            properties.load(inputStream);
        } catch (IOException e) {}
    }

    public static String getProperty(String key) {
        String value = System.getenv(key);
        if (Objects.isNull(value)) value = System.getProperty(key);
        if(Objects.isNull(value)) value = properties.getProperty(key);
        return value;
    }

}
