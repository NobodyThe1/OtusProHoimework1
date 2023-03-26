package factories;

import com.opera.core.systems.OperaDriver;
import data.BrowserData;
import exceptions.BrowserNotSupportedException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.Locale;

public class WebDriverFactory implements IWebDriverFactory {

    private String browserName = System.getProperty("browser", "chrome").toLowerCase(Locale.ROOT);

    public EventFiringWebDriver create() {
        switch (this.browserName) {
            case "chrome": {
                WebDriverManager.chromedriver().setup();
                return new EventFiringWebDriver(new ChromeDriver());
            }
            default:
                try {
                    throw new BrowserNotSupportedException(this.browserName);
                } catch (BrowserNotSupportedException ex) {
                    ex.printStackTrace();
                    return null;
                }
        }
    }
}