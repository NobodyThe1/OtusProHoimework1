package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import waiters.Waiter;

public abstract class AbsPageObject<T> {

    protected WebDriver driver;
    protected Actions actions;
    protected Waiter waiter;

    public AbsPageObject (WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
        this.waiter = new Waiter(driver);

        PageFactory.initElements(driver,  this);
    }
}
