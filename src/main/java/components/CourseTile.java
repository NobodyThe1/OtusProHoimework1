package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.stream.Collectors;
import data.MonthData;
import pages.AnyPage;

import java.time.LocalDate;
import java.util.stream.Stream;

public class CourseTile extends AbsComponent<CourseTile> {

    public CourseTile(WebDriver driver) {
        super(driver);
    }

    //String courseTitleLocator = "//*[contains (text(), '%s')]";

    @FindBy(css = ".lessons__new-item-time")
    private List<WebElement> courseSpecializationStart;

    @FindBy(css = ".lessons__new-item-start")
    private List<WebElement> courseStart;

    public void findCourseByTitle(String title) {
        moveAndClick(driver.findElement(By.xpath(String.format(courseTitleLocator, title))));
    }

    public List<LocalDate> getEarlierCourse() {
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

        List<LocalDate> allCoursesStartDate = new ArrayList<>();

        for (String dateOfStart : allCoursesStartString) {
            String monthOfStart = dateOfStart.split(" ")[1];
            dateOfStart = dateOfStart.replaceAll("[а-я]+", String.format("%d", MonthData.getDate(monthOfStart).getNumber()));
            dateOfStart += " " + LocalDate.now().getYear();
            if (dateOfStart.equals("О дате старта будет объявлено позже")) {
                allCoursesStartDate.add(null);
            } else if (dateOfStart.equals("В сентябре")) {
                allCoursesStartDate.add(LocalDate.of(2023, 9, 29));
            } else {
                allCoursesStartDate.add(LocalDate.parse(dateOfStart, DateTimeFormatter.ofPattern("d M yyyy", Locale.ROOT)));
            }
        }
        return allCoursesStartDate;
    }
}
