package components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import data.MonthData;
import java.time.LocalDate;
import java.util.stream.Stream;

public class CourseTile extends AbsComponent<CourseTile> {

    public CourseTile(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".lessons__new-item-title")
    private List<WebElement> courseTitle;

    @FindBy(css = ".lessons__new-item-time")
    private List<WebElement> courseSpecializationStart;

    @FindBy(css = ".lessons__new-item-start")
    private List<WebElement> courseStart;

    public void getEarlierCourse() {
        List<LocalDateTime> allCoursesStarsDate = new ArrayList<>();

        List<String> courseSpecializationStartString = courseSpecializationStart.stream()
                .map(WebElement::getText)
                .filter(String -> String.length() > 10)
                .map(String -> (String.replaceAll("\\s\\d.*", "")))
                .collect(Collectors.toList());

        List<String> courseStartString = courseStart.stream()
                .map(WebElement::getText)
                .map(String -> (String.replaceAll("[С]\\s", "")))
                .collect(Collectors.toList());

        List<String> allCoursesStartString = new ArrayList<>();
        Stream.of(courseSpecializationStartString, courseStartString)
                .forEach(allCoursesStartString::addAll);

       // List<> allCoursesStartDate =
                allCoursesStartString.stream()
                        .map((String dateOfEvent) ->
                        {String monthOfEvent = dateOfEvent.split("\\s+")[1];
                            dateOfEvent = dateOfEvent.replaceAll("[а-я]+", String.format("%d", MonthData.getDate(monthOfEvent).getNumber()));
                            dateOfEvent += " " + LocalDate.now().getYear();

                    if (dateOfEvent.equals("О дате старта будет объявлено позже")) {
                        return null;
                    } else if (dateOfEvent.equals("В сентябре")) {
                        return LocalDate.of(2023, 9, 29);
                    } else {
                        return LocalDateTime.parse(dateOfEvent, DateTimeFormatter.ofPattern("d M yyyy"));
                    }


                })
                .forEach(allCoursesStartString::sort);
    }
}
