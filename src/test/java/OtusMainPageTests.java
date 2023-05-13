import annotations.Driver;
import components.CourseTile;
import extensions.UIExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import pages.AnyPage;
import pages.MainPage;

@ExtendWith(UIExtension.class)
public class OtusMainPageTests {

@Driver
private WebDriver driver;

    @Test
    public void moveToCourseTile() {
        new MainPage(driver)
                .open();
        new CourseTile(driver)
                .moveToCourse("Специализация Administrator Linux");
    }

    @Test
    public void filterCourseByTitle() {
        new MainPage(driver)
                .open();
        new CourseTile(driver)
                .findCourseByTitle("Специализация Administrator Linux");
        new AnyPage(driver)
                .rightPageShouldBeOpened("Специализация Administrator Linux");
    }

    @Test
    public void getCourseDate() {
        new MainPage(driver)
                .open();
        new CourseTile(driver)
                .getEarlierLaterCourse(true);
    }
}
