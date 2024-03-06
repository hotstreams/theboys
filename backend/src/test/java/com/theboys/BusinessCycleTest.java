package com.theboys;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BusinessCycleTest extends BaseFirefoxTest {
    private static final String URL = "http://localhost:5173/";

    private void signInAsManager() {
        driver.get(URL + "signin");

        WebElement username = driver.findElement(By.xpath("//input[@type='username']"));
        username.sendKeys("manager1");

        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys("password");

        WebElement button = driver.findElement(By.xpath("//button[text()='Sign in']"));
        button.click();
    }

    private void signInAsCustomer() {
        driver.get(URL + "signin");

        WebElement username = driver.findElement(By.xpath("//input[@type='username']"));
        username.sendKeys("customer");

        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys("password");

        WebElement button = driver.findElement(By.xpath("//button[text()='Sign in']"));
        button.click();
    }

    private void signInAsHero() {
        driver.get(URL + "signin");

        WebElement username = driver.findElement(By.xpath("//input[@type='username']"));
        username.sendKeys("hero1");

        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys("password");

        WebElement button = driver.findElement(By.xpath("//button[text()='Sign in']"));
        button.click();
    }

    private void signInAsScientist() {
        driver.get(URL + "signin");

        WebElement username = driver.findElement(By.xpath("//input[@type='username']"));
        username.sendKeys("scientist1");

        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys("password");

        WebElement button = driver.findElement(By.xpath("//button[text()='Sign in']"));
        button.click();
    }

    private String getRandomUsername() {
        return new Random().ints(97, 122 + 1)
                .limit(8)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private void signInAsVisitor() {
        driver.get(URL + "signup");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        final String user = getRandomUsername();

        WebElement username = driver.findElement(By.xpath("//input[@type='username']"));
        username.sendKeys(user);

        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys("password");

        WebElement button = driver.findElement(By.xpath("//button[text()='Sign up']"));
        button.click();

        driver.get(URL + "signin");

        username = driver.findElement(By.xpath("//input[@type='username']"));
        username.sendKeys(user);

        password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys("password");

        button = driver.findElement(By.xpath("//button[text()='Sign in']"));
        button.click();
    }

    private void rentHero() {
        driver.get(URL + "rent-a-hero");

        WebElement rentButton = driver.findElement(By.xpath("//a[text()='Rent']"));
        rentButton.click();

        WebElement description = driver.findElement(By.xpath("//textarea"));
        description.sendKeys("Test renting123123");

        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("document.getElementsByName('start')[0].setAttribute(\"value\", \"" + LocalDate.now() + "\")" );
        executor.executeScript("document.getElementsByName('end')[0].setAttribute(\"value\", \"" + LocalDate.now().plusDays(1) + "\")" );

        WebElement send = driver.findElement(By.xpath("//button[text()='Send']"));
        send.click();

        assertEquals(URL + "rent-requests", driver.getCurrentUrl());
    }

    private void signOut() {
        WebElement imageButton = driver.findElement(By.id("hs-dropdown-with-header"));
        imageButton.click();

        WebElement signOut = driver.findElement(By.xpath("//a[text()='Sign out']"));
        signOut.click();
    }

    private void processCustomerRequest() {
        WebElement imageButton = driver.findElement(By.id("hs-dropdown-with-header"));
        imageButton.click();

        WebElement customerRequests = driver.findElement(By.xpath("//a[text()='Entrepreneur requests']"));
        customerRequests.click();

        Select status = new Select(driver.findElement(By.xpath("//tr[last()]//select")));
        status.selectByValue("IN_PROGRESS");
    }

    private void acceptCustomerRequestAsHero() {
        WebElement imageButton = driver.findElement(By.id("hs-dropdown-with-header"));
        imageButton.click();

        WebElement customerRequests = driver.findElement(By.xpath("//a[text()='Jobs']"));
        customerRequests.click();

        WebElement button = driver.findElement(By.xpath("//button[text()='Confirm']"));
        button.click();
    }

    private void acceptCustomerRequestAsManager() {
        WebElement imageButton = driver.findElement(By.id("hs-dropdown-with-header"));
        imageButton.click();

        WebElement customerRequests = driver.findElement(By.xpath("//a[text()='Entrepreneur requests']"));
        customerRequests.click();

        Select status = new Select(driver.findElement(By.xpath("//tr[last()]//select")));
        status.selectByValue("CONFIRMED");
    }

    private void findCandidate() {
        driver.get(URL + "find-candidate");

        WebElement firstName = driver.findElement(By.name("first-name"));
        firstName.sendKeys("Ivan");

        WebElement lastName = driver.findElement(By.name("last-name"));
        lastName.sendKeys("Ivanov");

        Select sex = new Select(driver.findElement(By.name("sex")));
        sex.selectByIndex(0);

        Select race = new Select(driver.findElement(By.name("race")));
        race.selectByIndex(0);

        WebElement descr = driver.findElement(By.name("about"));
        descr.sendKeys("Testik");

        WebElement send = driver.findElement(By.xpath("//button[text()='Send']"));
        send.click();

        WebElement status = driver.findElement(By.xpath("//span[text()='PENDING']"));
        assertNotNull(status);
    }

    private void acceptFindCandidateRequestAsManager() {
        WebElement imageButton = driver.findElement(By.id("hs-dropdown-with-header"));
        imageButton.click();

        WebElement customerRequests = driver.findElement(By.xpath("//a[text()='Scientist requests']"));
        customerRequests.click();

        Select status = new Select(driver.findElement(By.xpath("//tr[last()]//select")));
        status.selectByValue("IN_PROGRESS");
    }

    private void createVacancy() {
        WebElement imageButton = driver.findElement(By.id("hs-dropdown-with-header"));
        imageButton.click();

        WebElement customerRequests = driver.findElement(By.xpath("//a[text()='Vacancy requests']"));
        customerRequests.click();

        WebElement open = driver.findElement(By.xpath("//a[text()='Open vacancy']"));
        open.click();

        WebElement firstName = driver.findElement(By.name("first-name"));
        firstName.sendKeys("Ivan");

        WebElement lastName = driver.findElement(By.name("last-name"));
        lastName.sendKeys("Ivanov");

        WebElement button = driver.findElement(By.xpath("//a[text()='Open']"));
        button.click();
    }

    private void applyToVacancy() {
        WebElement vacancies = driver.findElement(By.xpath("//a[text()='Vacancies']"));
        vacancies.click();

        WebElement apply = driver.findElement(By.xpath("//a[text()='Apply']"));
        apply.click();

        WebElement firstName = driver.findElement(By.name("first-name"));
        firstName.sendKeys("Ivan");

        WebElement lastName = driver.findElement(By.name("last-name"));
        lastName.sendKeys("Ivanov");

        WebElement phone = driver.findElement(By.name("phone"));
        phone.sendKeys("8-904-123-12-12");

        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("document.getElementsByName('birth')[0].setAttribute(\"value\", \"" + LocalDate.now() + "\")" );

        Select sex = new Select(driver.findElement(By.name("sex")));
        sex.selectByIndex(0);

        Select race = new Select(driver.findElement(By.name("race")));
        race.selectByIndex(0);

        WebElement height = driver.findElement(By.name("height"));
        height.sendKeys("200");

        WebElement weight = driver.findElement(By.name("weight"));
        weight.sendKeys("200");

        WebElement address = driver.findElement(By.name("street-address"));
        address.sendKeys("Nevskiy 228");

        WebElement descr = driver.findElement(By.name("about"));
        descr.sendKeys("Testik");

        File file = new File("./src/test/resources/vought_logo.png");
        WebElement photo = driver.findElement(By.name("file-upload-photo"));
        photo.sendKeys(file.getAbsolutePath());

        WebElement med = driver.findElement(By.name("file-upload-pdf"));
        med.sendKeys(file.getAbsolutePath());

        WebElement send = driver.findElement(By.xpath("//button[text()='Send']"));
        send.click();

        WebElement status = driver.findElement(By.xpath("//span[text()='WAITING']"));
        assertNotNull(status);
    }

    private void acceptCandidateRequest() {
        WebElement imageButton = driver.findElement(By.id("hs-dropdown-with-header"));
        imageButton.click();

        WebElement customerRequests = driver.findElement(By.xpath("//a[text()='Candidate requests']"));
        customerRequests.click();

        WebElement apply = driver.findElement(By.xpath("//a[text()='View']"));
        apply.click();

        Select status = new Select(driver.findElement(By.id("selectId")));
        status.selectByValue("IN_PROGRESS");

        WebElement send = driver.findElement(By.xpath("//button[text()='Save']"));
        send.click();
    }

    private void fulfilScientistFindCandidateRequest() {
        WebElement imageButton = driver.findElement(By.id("hs-dropdown-with-header"));
        imageButton.click();

        WebElement customerRequests = driver.findElement(By.xpath("//a[text()='Scientist requests']"));
        customerRequests.click();

        Select status = new Select(driver.findElement(By.xpath("//tr[last()]//select")));
        status.selectByValue("FULFILLED");
    }

    private void checkCandidateRequest() {
        WebElement imageButton = driver.findElement(By.id("hs-dropdown-with-header"));
        imageButton.click();

        WebElement customerRequests = driver.findElement(By.xpath("//a[text()='Candidate requests']"));
        customerRequests.click();

        WebElement status = driver.findElement(By.xpath("//span[text()='FULFILLED']"));
        assertNotNull(status);
    }

    @Test
    public void mainBusinessProcessShouldEndSuccessfully() {
        signInAsCustomer();

        rentHero();

        signOut();

        signInAsManager();

        processCustomerRequest();

        signOut();

        signInAsHero();

        acceptCustomerRequestAsHero();

        signOut();

        signInAsManager();

        acceptCustomerRequestAsManager();

        signOut();

        signInAsScientist();

        findCandidate();

        signOut();

        signInAsManager();

        acceptFindCandidateRequestAsManager();

        createVacancy();

        signOut();

        signInAsVisitor();

        applyToVacancy();

        signOut();

        signInAsManager();

        acceptCandidateRequest();

        fulfilScientistFindCandidateRequest();

        signOut();

        signInAsScientist();

        checkCandidateRequest();

        signOut();
    }
}
