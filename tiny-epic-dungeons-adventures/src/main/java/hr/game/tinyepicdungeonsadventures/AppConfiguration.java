package hr.game.tinyepicdungeonsadventures;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Data
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConfiguration {

    private static final String CONFIGURATION_FILE_NAME = "application.properties";
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = AppConfiguration.class.getClassLoader().getResourceAsStream(CONFIGURATION_FILE_NAME)) {
            if (input == null) {
                log.error("Unable to find {}. Make sure it is defined in resources folder.", CONFIGURATION_FILE_NAME);
            } else {
                properties.load(input);
            }
        } catch (IOException ex) {
            log.error("Failed to load configuration file {}", CONFIGURATION_FILE_NAME, ex);
        }
    }

    public static String getHost() {
        return properties.getProperty("rmi.host");
    }

    public static int getPort() {
        return Integer.parseInt(properties.getProperty("rmi.port"));
    }

    public static String getServiceName() {
        return properties.getProperty("chat.service.name");
    }
}
