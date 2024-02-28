package com.theboys;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class UserInterfaceTest extends BaseFirefoxTest {
    private static final String URL = "http://localhost:5173/";

    @Test
    public void signinShouldBeSuccessfulWithCorrectCredentials() {
        driver.get(URL + "signin");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement username = driver.findElement(By.xpath("//input[@type='username']"));
        username.sendKeys("manager1");

        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys("password");

        WebElement button = driver.findElement(By.xpath("//button[text()='Sign in']"));
        button.click();

        assertEquals(URL, driver.getCurrentUrl());
    }

    @Test
    //Fix it
    public void signinShouldBeUnsuccessfulWithIncorrectCredentials() {
        driver.get(URL + "signin");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement username = driver.findElement(By.xpath("//input[@type='username']"));
        username.sendKeys("manager1");

        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys("incorrectpassword");

        WebElement button = driver.findElement(By.xpath("//button[text()='Sign in']"));
        button.click();

        assertDoesNotThrow(() -> driver.findElement(By.xpath("//div[text()='Error occured during sending request']")));
    }

    @Test
    public void signupShouldBeSuccessfulWithCorrectCredentials() {
        driver.get(URL + "signup");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement username = driver.findElement(By.xpath("//input[@type='username']"));
        username.sendKeys(getRandomUsername());

        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys("password3");

        WebElement button = driver.findElement(By.xpath("//button[text()='Sign up']"));
        button.click();

        assertDoesNotThrow(() -> driver.findElement(By.xpath("//div[text()='You were successfully registered!']")));
    }

    @Test
    public void signupShouldBeUnsuccessfulWithIncorrectCredentials() {
        driver.get(URL + "signup");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement username = driver.findElement(By.xpath("//input[@type='username']"));
        username.sendKeys("kek");

        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys("shrek");

        WebElement button = driver.findElement(By.xpath("//button[text()='Sign up']"));
        button.click();

        assertDoesNotThrow(() -> driver.findElement(By.xpath("//div[text()='Fields shoud be min 8 characters']")));
    }

    @Test
    //Fix me
    public void customerSpecifiedHeroRentShouldBeSuccessful() {
        driver.get(URL + "signin");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement username = driver.findElement(By.xpath("//input[@type='username']"));
        username.sendKeys("customer");

        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys("password");

        WebElement button = driver.findElement(By.xpath("//button[text()='Sign in']"));
        button.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        driver.get(URL + "rent-a-hero");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebElement rentButton = driver.findElement(By.xpath("//a[text()='Rent']"));
        rentButton.click();

        WebElement description = driver.findElement(By.xpath("//textarea"));
        description.sendKeys("Test renting");

        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("document.getElementsByName('start')[0].setAttribute(\"value\", \"" + LocalDate.now() + "\")" );
        executor.executeScript("document.getElementsByName('end')[0].setAttribute(\"value\", \"" + LocalDate.now().plusDays(1) + "\")" );

        WebElement send = driver.findElement(By.xpath("//button[text()='Send']"));
        send.click();

        assertEquals(URL + "rent-requests", driver.getCurrentUrl());
    }

    @Test
    //Fix me
    public void customerSpecifiedHeroRentShouldBeUnsuccessfulWhenDateIsNull() {
        driver.get(URL + "signin");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement username = driver.findElement(By.xpath("//input[@type='username']"));
        username.sendKeys("customer");

        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys("password");

        WebElement button = driver.findElement(By.xpath("//button[text()='Sign in']"));
        button.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        driver.get(URL + "rent-a-hero");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebElement rentButton = driver.findElement(By.xpath("//a[text()='Rent']"));
        rentButton.click();

        WebElement description = driver.findElement(By.xpath("//textarea"));
        description.sendKeys("Test renting");

        WebElement send = driver.findElement(By.xpath("//button[text()='Send']"));
        send.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebElement alert = driver.findElement(By.xpath("//div[text()='Dates should be XXXX-XX-XX']"));

        assertNotNull(alert);
    }

    @Test
    public void customerSpecifiedHeroRentShouldBeUnsuccessfulWhenDescriptionIsNull() {
        driver.get(URL + "signin");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement username = driver.findElement(By.xpath("//input[@type='username']"));
        username.sendKeys("customer");

        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys("password");

        WebElement button = driver.findElement(By.xpath("//button[text()='Sign in']"));
        button.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        driver.get(URL + "rent-a-hero");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebElement rentButton = driver.findElement(By.xpath("//a[text()='Rent']"));
        rentButton.click();

        WebElement send = driver.findElement(By.xpath("//button[text()='Send']"));
        send.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        WebElement alert = driver.findElement(By.xpath("//div[text()='All fields should be filled out']"));

        assertNotNull(alert);
    }

    private String getRandomUsername() {
        return new Random().ints(97, 122 + 1)
                .limit(8)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @Test
    //Fix me
    public void visitorBecomeHeroRequestSuccessfullySubmitted() {
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

        driver.get(URL + "become-a-hero");

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

    private void signInAsRandomUser() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        driver.get(URL + "signup");

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

    @Test
    public void visitorBecomeHeroRequestUnsuccessfullWhenFirstNameIsNull() {
        signInAsRandomUser();

        driver.get(URL + "become-a-hero");

        WebElement firstName = driver.findElement(By.name("first-name"));
        firstName.sendKeys("");

        WebElement send = driver.findElement(By.xpath("//button[text()='Send']"));
        send.click();

        WebElement alert = driver.findElement(By.xpath("//div[text()='All fields should be filled out']"));
        assertNotNull(alert);
    }

    @Test
    public void visitorBecomeHeroRequestUnsuccessfullWhenLastNameIsNull() {
        signInAsRandomUser();

        driver.get(URL + "become-a-hero");

        WebElement firstName = driver.findElement(By.name("first-name"));
        firstName.sendKeys("Ivan");

        WebElement lastName = driver.findElement(By.name("last-name"));
        lastName.sendKeys("");

        WebElement send = driver.findElement(By.xpath("//button[text()='Send']"));
        send.click();

        WebElement alert = driver.findElement(By.xpath("//div[text()='All fields should be filled out']"));
        assertNotNull(alert);
    }

    @Test
    public void visitorBecomeHeroRequestUnsuccessfullWhenPhoneIsNull() {
        signInAsRandomUser();

        driver.get(URL + "become-a-hero");

        WebElement firstName = driver.findElement(By.name("first-name"));
        firstName.sendKeys("Ivan");

        WebElement lastName = driver.findElement(By.name("last-name"));
        lastName.sendKeys("Ivanov");

        WebElement phone = driver.findElement(By.name("phone"));
        phone.sendKeys("");

        WebElement send = driver.findElement(By.xpath("//button[text()='Send']"));
        send.click();

        WebElement alert = driver.findElement(By.xpath("//div[text()='All fields should be filled out']"));
        assertNotNull(alert);
    }

    @Test
    public void visitorBecomeHeroRequestUnsuccessfullWhenDateIsNull() {
        signInAsRandomUser();

        driver.get(URL + "become-a-hero");

        WebElement firstName = driver.findElement(By.name("first-name"));
        firstName.sendKeys("Ivan");

        WebElement lastName = driver.findElement(By.name("last-name"));
        lastName.sendKeys("Ivanov");

        WebElement phone = driver.findElement(By.name("phone"));
        phone.sendKeys("8-123-121-12-12");

        WebElement send = driver.findElement(By.xpath("//button[text()='Send']"));
        send.click();

        WebElement alert = driver.findElement(By.xpath("//div[text()='All fields should be filled out']"));
        assertNotNull(alert);
    }

    @Test
    //Fix me
    public void visitorBecomeHeroRequestUnsuccessfullWhenHeightAndWeightIsNull() {
        signInAsRandomUser();

        driver.get(URL + "become-a-hero");

        WebElement firstName = driver.findElement(By.name("first-name"));
        firstName.sendKeys("Ivan");

        WebElement lastName = driver.findElement(By.name("last-name"));
        lastName.sendKeys("Ivanov");

        WebElement phone = driver.findElement(By.name("phone"));
        phone.sendKeys("8-123-121-12-12");

        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("document.getElementsByName('birth')[0].setAttribute(\"value\", \"" + LocalDate.now() + "\")" );

        WebElement height = driver.findElement(By.name("height"));
        height.sendKeys("");

        WebElement weight = driver.findElement(By.name("weight"));
        weight.sendKeys("");

        WebElement send = driver.findElement(By.xpath("//button[text()='Send']"));
        send.click();

        WebElement alert = driver.findElement(By.xpath("//div[text()='All fields should be filled out']"));
        assertNotNull(alert);
    }

    @Test
    //Fix me
    public void visitorBecomeHeroRequestUnsuccessfullWhenAddressAndDescrIsNull() {
        signInAsRandomUser();

        driver.get(URL + "become-a-hero");

        WebElement firstName = driver.findElement(By.name("first-name"));
        firstName.sendKeys("Ivan");

        WebElement lastName = driver.findElement(By.name("last-name"));
        lastName.sendKeys("Ivanov");

        WebElement phone = driver.findElement(By.name("phone"));
        phone.sendKeys("8-123-121-12-12");

        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("document.getElementsByName('birth')[0].setAttribute(\"value\", \"" + LocalDate.now() + "\")" );

        WebElement height = driver.findElement(By.name("height"));
        height.sendKeys("185");

        WebElement weight = driver.findElement(By.name("weight"));
        weight.sendKeys("185");

        WebElement address = driver.findElement(By.name("street-address"));
        address.sendKeys("");

        WebElement description = driver.findElement(By.name("about"));
        description.sendKeys("");

        File file = new File("./src/test/resources/vought_logo.png");
        WebElement photo = driver.findElement(By.name("file-upload-photo"));
        photo.sendKeys(file.getAbsolutePath());

        WebElement med = driver.findElement(By.name("file-upload-pdf"));
        med.sendKeys(file.getAbsolutePath());

        WebElement send = driver.findElement(By.xpath("//button[text()='Send']"));
        send.click();

        WebElement alert = driver.findElement(By.xpath("//div[text()='All fields should be filled out']"));
        assertNotNull(alert);
    }

    @Test
    //Fix me
    public void visitorBecomeHeroRequestUnsuccessfullWhenPhoneIsNotValid() {
        signInAsRandomUser();

        driver.get(URL + "become-a-hero");

        WebElement firstName = driver.findElement(By.name("first-name"));
        firstName.sendKeys("Ivan");

        WebElement lastName = driver.findElement(By.name("last-name"));
        lastName.sendKeys("Ivanov");

        WebElement phone = driver.findElement(By.name("phone"));
        phone.sendKeys("3228");

        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("document.getElementsByName('birth')[0].setAttribute(\"value\", \"" + LocalDate.now() + "\")" );

        WebElement height = driver.findElement(By.name("height"));
        height.sendKeys("185");

        WebElement weight = driver.findElement(By.name("weight"));
        weight.sendKeys("185");

        WebElement address = driver.findElement(By.name("street-address"));
        address.sendKeys("123");

        WebElement description = driver.findElement(By.name("about"));
        description.sendKeys("123");

        File file = new File("./src/test/resources/vought_logo.png");
        WebElement photo = driver.findElement(By.name("file-upload-photo"));
        photo.sendKeys(file.getAbsolutePath());

        WebElement med = driver.findElement(By.name("file-upload-pdf"));
        med.sendKeys(file.getAbsolutePath());

        WebElement send = driver.findElement(By.xpath("//button[text()='Send']"));
        send.click();

        WebElement alert = driver.findElement(By.xpath("//div[text()='Phone number shoud be X-XXX-XXX-XX-XX']"));
        assertNotNull(alert);
    }

    @Test
    public void managerShouldHaveSpecificFunctionality() {
        driver.get(URL + "signin");

        WebElement username = driver.findElement(By.xpath("//input[@type='username']"));
        username.sendKeys("manager1");

        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys("password");

        WebElement button = driver.findElement(By.xpath("//button[text()='Sign in']"));
        button.click();

        WebElement imageButton = driver.findElement(By.id("hs-dropdown-with-header"));
        imageButton.click();

        WebElement candidateRequests = driver.findElement(By.xpath("//a[text()='Candidate requests']"));
        WebElement entrepreneurRequests = driver.findElement(By.xpath("//a[text()='Entrepreneur requests']"));
        WebElement scientistRequests = driver.findElement(By.xpath("//a[text()='Scientist requests']"));

        assertNotNull(candidateRequests);
        assertNotNull(entrepreneurRequests);
        assertNotNull(scientistRequests);
    }

    @Test
    public void customerShouldHaveSpecificFunctionality() {
        driver.get(URL + "signin");

        WebElement username = driver.findElement(By.xpath("//input[@type='username']"));
        username.sendKeys("customer");

        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys("password");

        WebElement button = driver.findElement(By.xpath("//button[text()='Sign in']"));
        button.click();

        WebElement imageButton = driver.findElement(By.id("hs-dropdown-with-header"));
        imageButton.click();

        WebElement rentAHero = driver.findElement(By.xpath("//a[text()='Rent a hero']"));
        WebElement rentRequests = driver.findElement(By.xpath("//a[text()='Rent requests']"));

        assertNotNull(rentAHero);
        assertNotNull(rentRequests);
    }
}
