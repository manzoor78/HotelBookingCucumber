package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadProperties {
    Properties properties = new Properties();

    public void loadPropertyFile() throws FileNotFoundException, IOException {
        String filePath = System.getProperty("user.dir") + "/src/main/java/config/config.properties";
        File file = new File(filePath);
        properties.load(new FileInputStream(file));
    }

    public String getBrowser() {
        return properties.getProperty("browser");
    }

    public String getURL() {
        return properties.getProperty("url");
    }
}
