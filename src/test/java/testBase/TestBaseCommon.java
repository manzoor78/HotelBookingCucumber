package testBase;

import utilities.BrowserDriver;
import utilities.ReadProperties;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TestBaseCommon {
    public ReadProperties propertyFile = new ReadProperties();

    public void initialise() throws FileNotFoundException, IOException, InterruptedException {
        propertyFile.loadPropertyFile();
        BrowserDriver.browser(propertyFile.getBrowser());
        BrowserDriver.driver.get(propertyFile.getURL());
        Thread.sleep(4000); // TODO: Replace this with better waiting mechanism
    }
}
