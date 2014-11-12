package backend;

import backend.test_memory_base.AccountServiceImpMemory;
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

/**
 * Created by narek on 24.10.14.
 */
public class LoginTest {

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
// Now submit the form. WebDriver will find the form for us from the element
        elementUsername.submit();
        elementPassword.submit();
        elementEmail.submit();
// Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            @NotNull
            public Boolean apply(@NotNull WebDriver d) {
                final String result = d.findElement(By.name("message")).getText();
                System.out.println(d.findElement(By.name("message")).getText());
                return (result.equals("Поздравляем, вы зарегистированы!"));
            }
        });

        driver.quit();
    }
    public static void testLogin(@NotNull String url,@NotNull String username,@NotNull String password) {
        WebDriver driver = new HtmlUnitDriver(true);
        driver.get(url);
// Find the text input element by its name
        WebElement elementUsername = driver.findElement(By.name("login"));
                elementUsername.sendKeys(username);
        WebElement elementPassword = driver.findElement(By.name("password"));
                elementPassword.sendKeys(password);
// Now submit the form. WebDriver will find the form for us from the element
        elementUsername.submit();
        elementPassword.submit();
// Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            @NotNull
            public Boolean apply(@NotNull WebDriver d) {
                final String result = d.findElement(By.name("message")).getText();
                return (result.equals("Вход успешен"));
            }
        });
        driver.quit();
    }

    public static void main(String[] args) throws Exception {
        AccountService pool=new AccountServiceImpMemory();//глобальный пул юзеров и их сессий, сейчас из памяти все

        Auth auth = new Auth(pool);
        LogOff logoff = new LogOff(pool);
        Register register=new Register(pool);


        if (args.length != 1) {
            System.out.append("Use port as the first argument");
            System.exit(1);
        }

        String portString = args[0];
        int port = Integer.valueOf(portString);
        System.out.append("Starting at port: ").append(portString).append('\n');

        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        server.setHandler(context);

        context.addServlet(new ServletHolder(auth), "/authform");
        context.addServlet(new ServletHolder(logoff), "/logoff");
        context.addServlet(new ServletHolder(register),"/registration");
        context.addServlet(new ServletHolder(new AdminServlet(pool)), AdminServlet.adminPageURL);

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);

        server.start();
        server.join();
        testRegistrate("http://localhost:8080/registration", "naro91", "123", "naro91@mail.ru");
        //testLogin();
    }
}
