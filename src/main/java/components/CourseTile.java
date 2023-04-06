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

    public void moveToCourse(String title) {
        moveAndPerform(driver.findElement(By.xpath(String.format(courseTitleLocator, title))));
    }
    public void findCourseByTitle(String title) {
        moveAndClick(driver.findElement(By.xpath(String.format(courseTitleLocator, title))));
    }

    private List<LocalDate> allCoursesStartDate = new ArrayList<>();
    private List<String> allCoursesStart = new ArrayList<>();

    public List<String> getCoursesList() {
        List<String> courseSpecializationStartString = courseSpecializationStart.stream()
                .map(WebElement::getText)
                .filter(String -> String.length() > 10)
                .map(String -> (String.replaceAll("\\s\\d.*", "")))
                .collect(Collectors.toList());

        List<String> courseStartString = courseStart.stream()
                .map(WebElement::getText)
                .map(String -> (String.replaceAll("[С]\\s", "")))
                .collect(Collectors.toList());

        Stream.of(courseSpecializationStartString, courseStartString)
                .forEach(allCoursesStart::addAll);

        return allCoursesStart;
    }

    public List<LocalDate> getCourseDate() {

        for (String dateOfStart : allCoursesStart) {
            String monthOfStart = dateOfStart.split(" ")[1];
            dateOfStart = dateOfStart.replaceAll("[а-я]+", String.format("%d", MonthData.getDate(monthOfStart).getNumber()));
            dateOfStart += " " + LocalDate.now().getYear();
            if (dateOfStart.equals("О дате старта будет объявлено позже")) {
                return null;
            } else if (dateOfStart.equals("В сентябре")) {
                allCoursesStartDate.add(LocalDate.of(2025, 9, 29));
            } else {
                allCoursesStartDate.add(LocalDate.parse(dateOfStart, DateTimeFormatter.ofPattern("d M yyyy", Locale.ROOT)));
            }
        }
        return allCoursesStartDate;
                //.stream()
                //.reduce((item1, item2) -> !DateUtil.compareCourseDate(getCourseDate(item1), getCourseDate(item2)) ? item1 : item2)
                //.orElse(null);
    }
}
