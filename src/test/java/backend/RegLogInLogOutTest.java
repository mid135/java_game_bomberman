package backend;

import backend.test_memory_base.AccoutServiveImpMemory;
import com.sun.istack.internal.NotNull;
import frontend.AdminServlet;
import frontend.Auth;
import frontend.LogOff;
import frontend.Register;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
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
public class RegLogInLogOutTest {

    public static void testRegistrate(@NotNull String url,@NotNull String username,@NotNull String password, @NotNull String email) {
        WebDriver driver = new HtmlUnitDriver(true);
        driver.get(url);
// Find the text input element by its name
        WebElement elementUsername = driver.findElement(By.name("login"));
        elementUsername.sendKeys(username);
        WebElement elementPassword = driver.findElement(By.name("password"));
        elementPassword.sendKeys(password);
        WebElement elementEmail = driver.findElement(By.name("email"));
        elementEmail.sendKeys(email);
        WebElement elementButton = driver.findElement(By.id("inputData"));

// Now submit the form. WebDriver will find the form for us from the element
        elementButton.click();
// Wait for the page to load, timeout after 10 seconds
        try{
            (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
                @Override
                @NotNull
                public Boolean apply(@NotNull WebDriver d) {
                    final String result = d.findElement(By.id("mess")).getText();
                    System.out.println(result);
                    //assertEquals
                    return (result.equals("Поздравляем, вы зарегистированы!"));
                }
            });
            System.out.println("Тест на регистрацию пройден !");
        } catch(org.openqa.selenium.TimeoutException e) {
            System.out.println("Тест на регистрацию не пройден !");
        } finally {
            driver.quit();
        }
    }

    public static void testLoginLogOut(@NotNull String url,@NotNull String username,@NotNull String password) {
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
            (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
                @Override
                @NotNull
                public Boolean apply(@NotNull WebDriver d) {
                    final String result = d.findElement(By.id("mess")).getText();
                    System.out.println(result);
                    return (result.equals("Вход успешен"));
                }
            });
            System.out.println("Тест на авторизацию пройден !");
        }catch (org.openqa.selenium.TimeoutException e){
            System.out.println("Тест на авторизацию не пройден !");
        }

        elementButton = driver.findElement(By.id("inputData"));
        elementButton.click();
        try {
            (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
                @Override
                @NotNull
                public Boolean apply(@NotNull WebDriver d) {
                    final String result = d.findElement(By.id("mess")).getText();
                    System.out.println(result);
                    return (result.equals("Введите логин и пароль для входа"));
                }
            });
            System.out.println("Тест на LogOut пройден !");
        }catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Тест на LogOut пройден !");
        }finally {
            driver.quit();
        }

    }


    public static void main(String[] args) {
        testRegistrate("http://localhost:8080/registration", "naro91", "password", "naro91@mail.ru");
        testLoginLogOut("http://localhost:8080/authform", "naro91", "password");
    }

}
