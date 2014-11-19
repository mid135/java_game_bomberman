package backend;


import com.sun.istack.internal.NotNull;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeoutException;

/**
 * Created by narek on 24.10.14.
 */

public class RegLogInLogOutTest extends TestCase {

    public void startTest (WebDriver driver, String stirngForCheck) {
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            @NotNull
            public Boolean apply(@NotNull WebDriver d) {
                final String result = d.findElement(By.id("mess")).getText();
                assertEquals(result ,stirngForCheck);
                return (result.equals(stirngForCheck));
            }
        });
    }

    @Test
    public void registerUser (WebDriver driver) {
// Find the text input element by its name
        WebElement elementUsername = driver.findElement(By.name("login"));
        elementUsername.sendKeys("naro91");
        WebElement elementPassword = driver.findElement(By.name("password"));
        elementPassword.sendKeys("password");
        WebElement elementEmail = driver.findElement(By.name("email"));
        elementEmail.sendKeys("naro91@mail.ru");
        WebElement elementButton = driver.findElement(By.id("inputData"));

// Now submit the form. WebDriver will find the form for us from the element
        elementButton.click();
    }

    @Test
    public void testRegistrate() {
        WebDriver driver = new HtmlUnitDriver(true);
        driver.get("http://localhost:8080/registration");
        registerUser(driver);
// Wait for the page to load, timeout after 10 seconds
        try{
            startTest(driver, "Поздравляем, вы зарегистированы!");
            driver.get("http://localhost:8080/registration");
            registerUser(driver);
            startTest(driver, "Пользователь с таким именем уже зерегистрирован в системе!");
            //startTest(driver, "Пользователь с таким именем уже зерегистрирован в системе!");
        } catch(org.openqa.selenium.TimeoutException e) {
            System.out.println("Тест на регистрацию не пройден !");
        } finally {
            driver.quit();
        }
    }

    @Test
    public void testLoginLogOut(@NotNull String url,@NotNull String username,@NotNull String password) {
        WebDriver driver = new HtmlUnitDriver(true);
        driver.get(url);
// Find the text input element by its name
        WebElement elementUsername = driver.findElement(By.name("login"));
                elementUsername.sendKeys(username);
        WebElement elementPassword = driver.findElement(By.name("password"));
                elementPassword.sendKeys(password);
        WebElement elementButton = driver.findElement(By.id("inputData"));

// Now submit the form. WebDriver will find the form for us from the element
        elementButton.click();
// Wait for the page to load, timeout after 10 seconds
        try {
            startTest(driver, "Вход успешен");
            System.out.println("Тест на авторизацию пройден !");
        }catch (org.openqa.selenium.TimeoutException e){
            System.out.println("Тест на авторизацию не пройден !");
        }

        elementButton = driver.findElement(By.id("inputData"));
        elementButton.click();
        try {
            startTest(driver, "Введите логин и пароль для входа");
            System.out.println("Тест на LogOut пройден !");
        }catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Тест на LogOut пройден !");
        }finally {
            driver.quit();
        }

    }

}
