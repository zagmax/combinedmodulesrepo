package manager;

import lombok.extern.log4j.Log4j2;
import pages.CorePage;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Log4j2
public class PropertyManager {
    private static final String PROPERTIES_FILE = "/application.properties";

    public static Properties getPropertiesInstance() throws IOException {
        Properties instance = new Properties();
        log.info("trying to read properties file");
        try (
                InputStream resourceStream = CorePage.class.getResourceAsStream(PROPERTIES_FILE)
        ) {
            assert resourceStream != null;
            try (InputStreamReader inputStream = new InputStreamReader(resourceStream, StandardCharsets.UTF_8)
            ) {
                instance.load(inputStream);
            }
        }
        return instance;
    }

}
