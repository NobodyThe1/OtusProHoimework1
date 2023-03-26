package components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import data.MonthData;
import java.time.LocalDate;

public class CourseTile extends AbsComponent<CourseTile> {

    public CourseTile(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".lessons__new-item-title")
    private List<WebElement> courseTitle;

    @FindBy(css = ".lessons__new-item-time")
    private List<WebElement> courseSpecializationStart;

    @FindBy (css = ".lessons__new-item-start")
    private List<WebElement> courseStart;

public String getEarlierCourse() {
    List <LocalDate> courseSpecializationStartDate = courseSpecializationStart.stream()
            .map(WebElement::getText)
            .filter(String -> String.length() > 10)
            .map(String -> (String.replaceAll("\\s\\d.*", "")))
            .map((String dateOfStart) -> {
                String monthOfStart = dateOfStart.split("\\s+")[1];
                dateOfStart = dateOfStart.replaceAll("[а-я]+", String.format("%d", MonthData.getDate(monthOfStart).getNumber()));
                dateOfStart += " " + LocalDate.now().getYear();
                        return LocalDate.parse(dateOfStart, DateTimeFormatter.ofPattern("d M yyyy"));
                    }
            )
            .sorted(Comparator.naturalOrder())
            .collect(Collectors.toList());

    return courseSpecializationStartDate.get(0).toString();
}
}
