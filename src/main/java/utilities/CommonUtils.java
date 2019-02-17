package utilities;

import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CommonUtils {
    public ReadProperties propertyFile = new ReadProperties();

    public static boolean waitImplicitlyInSeconds(long time) {
        try {
            BrowserDriver.driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);

        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }

    public static WebDriverWait waitUntilCondition() {
        WebDriverWait wait = new WebDriverWait(BrowserDriver.driver, 20);
        return wait;
    }

    public void initialise() throws FileNotFoundException, IOException, InterruptedException {
        propertyFile.loadPropertyFile();
        BrowserDriver.browser(propertyFile.getBrowser());
        BrowserDriver.driver.get(propertyFile.getURL());
        Thread.sleep(4000);
    }

}
