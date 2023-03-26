import annotations.Driver;
import components.CourseTile;
import extensions.UIExtension;
import factories.WebDriverFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import pages.MainPage;

@ExtendWith(UIExtension.class)
public class OtusMainPageTests {

@Driver
private WebDriver driver;

    @Test
    public void getCourseDate() {
        new MainPage(driver)
                .open();
        new CourseTile(driver)
                .getEarlierCourse();
    }
}
