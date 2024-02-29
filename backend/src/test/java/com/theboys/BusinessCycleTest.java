package com.theboys;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Fix me
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

    private void acceptCustomerRequest() {
        WebElement imageButton = driver.findElement(By.id("hs-dropdown-with-header"));
        imageButton.click();

        WebElement customerRequests = driver.findElement(By.xpath("//a[text()='Entrepreneur requests']"));
        customerRequests.click();

        Select status = new Select(driver.findElement(By.xpath("//tr[last()]//select")));
        status.selectByValue("WAITING_FOR_CUSTOMER_APPROVAL");
    }
    @Test
    public void mainBusinessProcessShouldEndSuccessfully() {
        signInAsCustomer();

        rentHero();

        signOut();

        signInAsManager();

        processCustomerRequest();

        acceptCustomerRequest();
    }
}
